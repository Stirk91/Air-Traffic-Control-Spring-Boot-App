package com.example.atc.service;
import com.example.atc.FlightGen.PlaneGen;
import com.example.atc.dao.DataAccessService;
import com.example.atc.model.Gate;
import com.example.atc.model.Plane;
import com.example.atc.model.Runway;
import com.example.atc.model.Taxiway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Array;
import java.util.*;


public class AtcControlService implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    DataAccessService dataAccessService;

    int timeMultiplier = 2; // 1 = 60 secs; 2 = 30 secs
    Random random = new Random();

    public AtcControlService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // a nil UUID represents a null in the db for plane_id
    UUID nilPlaneId = new UUID (0,0 );

    @Override
    public void run(String... args) throws Exception {

        dataAccessService = new DataAccessService(jdbcTemplate);

        List<Plane> atcPlanes = dataAccessService.selectAllPlanes();

        // Sort planes by distance
        Collections.sort(atcPlanes, new Comparator<Plane>() {
            @Override
            public int compare(Plane p1, Plane p2) {
                return Integer.compare(p1.getDistance(), p2.getDistance());
            }
        });


        // stores 4 runways
        List<Runway> runwaysLogical = dataAccessService.selectAllRunways();

        // runwaysLogical must be sorted to ensure that id (1,2) and id (3,4) are together
        Collections.sort(runwaysLogical, new Comparator<Runway>() {
            @Override
            public int compare(Runway r1, Runway r2) {
                return Integer.compare(r1.getRunway_id(), r2.getRunway_id());
            }
        });

        Runway[] runway18L_36R = {runwaysLogical.get(0), runwaysLogical.get(1)};
        Runway[] runway18R_36L = {runwaysLogical.get(2), runwaysLogical.get(3)};

        List<Runway[]> runwaysAvailable = new ArrayList<Runway[]>();

        // Find available runways
        if (runway18L_36R[0].getPlane_id().equals(nilPlaneId) && runway18L_36R[1].getPlane_id().equals(nilPlaneId)) {
            runwaysAvailable.add(runway18L_36R);
        }
        if (runway18R_36L[0].getPlane_id().equals(nilPlaneId) && runway18R_36L[1].getPlane_id().equals(nilPlaneId)) {
            runwaysAvailable.add(runway18R_36L);
        }

        List<Taxiway> taxiways = dataAccessService.selectAllTaxiways();
        List<Taxiway> taxiwaysAvailable = new ArrayList<Taxiway>();

        List<Gate> gates = dataAccessService.selectAllGates();
        List<Gate> gatesAvailable = new ArrayList<Gate>();


        // Find available taxiways
        for (int i = 0; i < taxiways.size(); i++) {
            if (taxiways.get(i).getPlane_id().equals(nilPlaneId)) {
                taxiwaysAvailable.add(taxiways.get(i));
            }
        }

        // Find available gates
        for (int i = 0; i < gates.size(); i++) {
            if (gates.get(i).getPlane_id().equals(nilPlaneId)) {
                gatesAvailable.add(gates.get(i));
            }
        }

        int num_taxiing_from_gate = 0;

        System.out.println("Tick");

        // PLANE LOGIC
        // TODO needs to consider plane EMERGENCY (right now in PlaneGen there's 0% possibility of EMERGENCY

        for (int i = 0; i < atcPlanes.size(); i++) {

            Plane plane = atcPlanes.get(i);


            if (plane.getState().equals("TAKEOFF") && System.currentTimeMillis() - plane.getLast_action() > 3000) {
                dataAccessService.updateRunwayByPlaneId(plane.getId());
                plane.setState("OUTBOUND");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " is outbound");
            }


            else if (plane.getState().equals("TAXIING FROM GATE") && (runwaysAvailable.size() != 0) &&
                    (num_taxiing_from_gate < 3) &&
                    ( System.currentTimeMillis() - plane.getLast_action() > ( 25000 / timeMultiplier))) {
                dataAccessService.updateTaxiwayByPlaneId(plane.getId());
                runwaysAvailable.get(0)[0].setPlane_id(plane.getId()); // assign plane to first available runway
                dataAccessService.updateRunwayById(runwaysAvailable.get(0)[0].getRunway_id(), runwaysAvailable.get(0)[0]);
                runwaysAvailable.remove(0); // remove runway from runwaysAvailable
                plane.setState("TAKEOFF");
                updateLast_action(plane);
                num_taxiing_from_gate++;
                System.out.println("Plane " + plane.getTail_number() + " is taking off");
            }

            else if (plane.getState().equals("ARRIVED") && (taxiwaysAvailable.size() != 0) &&
                    ( System.currentTimeMillis() - plane.getLast_action() > ( 60000 / timeMultiplier))) {
                dataAccessService.updateGateByPlaneId(plane.getId());
                taxiwaysAvailable.get(0).setPlane_id(plane.getId()); // assign plane to first available taxiway
                dataAccessService.updateTaxiwayById(taxiwaysAvailable.get(0).getTaxiway_id(), taxiwaysAvailable.get(0));
                taxiwaysAvailable.remove(0);
                plane.setState("TAXIING FROM GATE");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " is taxiing from gate");
            }

            else if (plane.getState().equals("TAXIING TO GATE") && (gatesAvailable.size() != 0) &&
                    ( System.currentTimeMillis() - plane.getLast_action() > ( 25000 / timeMultiplier))) {
                dataAccessService.updateTaxiwayByPlaneId(plane.getId());
                gatesAvailable.get(0).setPlane_id(plane.getId()); // assign plane to first available gate
                dataAccessService.updateGateById(gatesAvailable.get(0).getGate_id(), gatesAvailable.get(0));
                gatesAvailable.remove(0); // remove gate from gatesAvailable
                plane.setState("ARRIVED");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " has arrived");
            }

            else if (plane.getState().equals("LANDED") && taxiwaysAvailable.size() != 0) {
                dataAccessService.updateRunwayByPlaneId(plane.getId()); // remove plane from runway in db
                taxiwaysAvailable.get(0).setPlane_id(plane.getId()); // assign plane to first available taxiway
                dataAccessService.updateTaxiwayById(taxiwaysAvailable.get(0).getTaxiway_id(), taxiwaysAvailable.get(0));
                taxiwaysAvailable.remove(0);
                plane.setState("TAXIING TO GATE");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " is taxiing to gate");
            }

            else if (plane.getState().equals("FINAL") && plane.getDistance() == 0) {
                plane.setState("LANDED");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " has landed");
            }

            else if (plane.getState().equals("INBOUND")  &&
                    (plane.getDistance() < 21120) && (runwaysAvailable.size() != 0) && (gatesAvailable.size() != 0)) {
                plane.setState("FINAL");
                updateLast_action(plane);

                // Runway assignment

                // Messy: should use something more intelligent
                if (plane.getHeading() >= 270 || plane.getHeading() < 90) {
                    // then assign to 36L or 36R
                    if (runwaysAvailable.get(0)[1].getRunway_name().equals("36R")) {
                        runwaysAvailable.get(0)[1].setPlane_id(plane.getId());
                        dataAccessService.updateRunwayById(runwaysAvailable.get(0)[1].getRunway_id(), runwaysAvailable.get(0)[1]);
                        runwaysAvailable.remove(0);

                        // Only assigns 36L if 36R is unavailable. Since it's in a different array, then if we get here, then 36R
                        // is not in the list of available runways, hence always use get(0)
                    } else if (runwaysAvailable.get(0)[1].getRunway_name().equals("36L")) {
                        System.out.println("line 186");
                        runwaysAvailable.get(0)[1].setPlane_id(plane.getId());
                        dataAccessService.updateRunwayById(runwaysAvailable.get(0)[1].getRunway_id(), runwaysAvailable.get(0)[1]);
                        runwaysAvailable.remove(0);
                    }
                }
                else if (plane.getHeading() < 270 || plane.getHeading() >= 90) {
                    if (runwaysAvailable.get(0)[0].getRunway_name().equals("18L")) {
                        runwaysAvailable.get(0)[0].setPlane_id(plane.getId());
                        dataAccessService.updateRunwayById(runwaysAvailable.get(0)[0].getRunway_id(), runwaysAvailable.get(0)[0]);
                        runwaysAvailable.remove(0);

                    } else if (runwaysAvailable.get(0)[0].getRunway_name().equals("18R")) {
                        runwaysAvailable.get(0)[0].setPlane_id(plane.getId());
                        dataAccessService.updateRunwayById(runwaysAvailable.get(0)[0].getRunway_id(), runwaysAvailable.get(0)[0]);
                        runwaysAvailable.remove(0);
                    }
                }
                    System.out.println("Plane " + plane.getTail_number() + " is on final");

            }

            else if (plane.getState().equals("INBOUND") &&
                    (plane.getDistance() < 21120) && (runwaysAvailable.size() == 0  ||
                    gatesAvailable.size() == 0)) {
                plane.setState("HOLDING");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " switched from inbound to holding");
            }

            else if (plane.getState().equals("HOLDING")  &&
                    (plane.getDistance() < 21120) && runwaysAvailable.size() != 0 && taxiwaysAvailable.size() != 0 &&
                    gatesAvailable.size() != 0) {
                plane.setState("INBOUND");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " switched from holding to inbound");
            }


            updateSpeed(plane);
            updateAltitude(plane);
            updateDistance(plane);
            updateHeading(plane);

            dataAccessService.updatePlaneById(plane.getId(), plane);
            }
    }


    private void updateLast_action(Plane plane) {
        plane.setLast_action(System.currentTimeMillis());
    }

    private void updateHeading(Plane plane) {

        int turnRate = (1 * 60) / timeMultiplier;

        if (plane.getState().equals("INBOUND")) {
            if (plane.getHeading() >= 270) {
                if (plane.getHeading() + turnRate >= 360) {
                    plane.setHeading((random.nextInt(2)));
                }
                else {
                    plane.setHeading(plane.getHeading() + turnRate);
                }
            }
            else if (plane.getHeading() < 90) {
                if (plane.getHeading() - turnRate <= 0) {
                    plane.setHeading(random.nextInt(2));
                }
                else {
                    plane.setHeading(plane.getHeading() - turnRate);
                }
            }

            else if (plane.getHeading() < 270) {
                if (plane.getHeading() - turnRate <= 180) {
                    plane.setHeading((random.nextInt(2) + 179));
                }
                else {
                    plane.setHeading(plane.getHeading() - turnRate);
                }
            }
            else if (plane.getHeading() >= 90) {
                if (plane.getHeading() + turnRate > 180) {
                    plane.setHeading((random.nextInt(2) + 179));
                }
                else {
                    plane.setHeading(plane.getHeading() + turnRate);
                }
            }
        }

        else if (plane.getState().equals("FINAL")) {
            // force plane heading to line up with runway
            Runway runway = dataAccessService.selectRunwayByPlaneId(plane.getId());
            if (runway.getRunway_name().equals("36R") || runway.getRunway_name().equals("36L")) {
                plane.setHeading(0);
            }
            if (runway.getRunway_name().equals("18R") || runway.getRunway_name().equals("18L")) {
                plane.setHeading(180);
            }
        }

        // circle around
        else if (plane.getState().equals("HOLDING")) {
            if (plane.getHeading() >= 0 && plane.getHeading() < 90) {
                if (plane.getHeading() + turnRate >= 90) {
                    plane.setHeading(90);
                }
                else {
                    plane.setHeading(plane.getHeading() + (random.nextInt(2) + turnRate));
                }
            }
            else if (plane.getHeading() >= 90 && plane.getHeading() < 180) {
                if (plane.getHeading() + turnRate >= 180) {
                    plane.setHeading(180);
                }
                else {
                    plane.setHeading(plane.getHeading() + (random.nextInt(2) + turnRate));
                }
            }
            else if (plane.getHeading() >= 180 && plane.getHeading() < 270) {
                if (plane.getHeading() + turnRate >= 270) {
                    plane.setHeading(270);
                }
                else {
                    plane.setHeading(plane.getHeading() + (random.nextInt(2) + turnRate));
                }
            }
            else if (plane.getHeading() >= 270 && plane.getHeading() < 360) {
                if (plane.getHeading() + turnRate >= 360) {
                    plane.setHeading(0);
                }
                else {
                    plane.setHeading(plane.getHeading() + (random.nextInt(2) + turnRate));
                }
            }

        }

        else if (plane.getState().equals("TAKEOFF")) {
            Runway runway = dataAccessService.selectRunwayByPlaneId(plane.getId());
            if (runway.getRunway_name().equals("36R") || runway.getRunway_name().equals("36L")) {
                plane.setHeading(0);
            }
            if (runway.getRunway_name().equals("18R") || runway.getRunway_name().equals("18L")) {
                plane.setHeading(180);
            }        }

        else if (plane.getState().equals("OUTBOUND")) {
            if ( (System.currentTimeMillis() - plane.getLast_action()) > 5000) {
                if (plane.getHeading() >= 360) {
                    plane.setHeading(0);
                }
             else if ( (System.currentTimeMillis() - plane.getLast_action()) > 5000 &&
                (System.currentTimeMillis() - plane.getLast_action()) < 10000 )
                    plane.setHeading(plane.getHeading() + random.nextInt(10) - random.nextInt(10));
            }
        }

        else {
            plane.setHeading(0);
        }
    }

    private void updateDistance(Plane plane) {

        if (plane.getState().equals("HOLDING")) {
            if (plane.getDistance() < 15840) {
                plane.setDistance(plane.getDistance() + ((plane.getSpeed() * 60) / timeMultiplier));
            }
            else {
                Random random = new Random();
                int rand = random.nextInt(51) - 50;
                plane.setDistance(plane.getDistance() + rand * 10);
            }
        }
        else if (plane.getState().equals("OUTBOUND")) {
            plane.setDistance(plane.getDistance() + ((plane.getSpeed() * 60) / timeMultiplier));
        }

        else {
            plane.setDistance(plane.getDistance() - ((plane.getSpeed() * 60) / timeMultiplier));
        }

        if ( plane.getDistance() <= 0){
            plane.setDistance(0);
        }

    }

    private void updateAltitude(Plane plane) {

        if (plane.getState().equals("INBOUND")) {
            if (plane.getAltitude() < 1000) {
                plane.setAltitude(plane.getAltitude() + (50 / timeMultiplier));
            }
            plane.setAltitude(plane.getAltitude() - (50 / timeMultiplier));
        }

        else if (plane.getState().equals("FINAL")) {
            plane.setAltitude(plane.getAltitude() - (6 * 60) / timeMultiplier); // 6 feet per sec
        }

        else if (plane.getState().equals("HOLDING")) {
            if ((plane.getAltitude() + (6 * 60) / timeMultiplier) > 3000) {
                plane.setAltitude(random.nextInt(11) + 3000);
            }
            else {
                plane.setAltitude(plane.getAltitude() + (6 * 60) / timeMultiplier); // 6 feet per sec
            }
        }

        else if (plane.getState().equals("OUTBOUND")) {
            if ((plane.getAltitude() + (50 * 60) / timeMultiplier) > 40000) {
                plane.setAltitude(40000);
            }
            else {
                plane.setAltitude(plane.getAltitude() + (50 * 60) / timeMultiplier);
            }
        }
        else {
            plane.setAltitude(0);
        }

        if ( plane.getAltitude() <= 0){
            plane.setAltitude(0);
        }
    }

    private void updateSpeed(Plane plane) {

        if (plane.getState().equals("INBOUND")) {
            if (plane.getSpeed() - (10 / timeMultiplier) < 200) {
                plane.setSpeed(200);
            }
            plane.setSpeed(plane.getSpeed() - ((random.nextInt(11)) / timeMultiplier));
        }

        else if (plane.getState().equals("FINAL")) {
            plane.setSpeed((random.nextInt(10) + 150));
        }

        else if (plane.getState().equals("LANDED")) {
            plane.setSpeed(0);
        }

        else if (plane.getState().equals("TAXIING TO GATE") || plane.getState().equals("TAXIING FROM GATE")) {
            plane.setSpeed((random.nextInt(5) + 15));
        }

        else if (plane.getState().equals("ARRIVED")) {
            plane.setSpeed(0);
        }

        else if (plane.getState().equals("TAKEOFF")) {
            plane.setSpeed((random.nextInt(10) + 165));
        }

        else if (plane.getState().equals("OUTBOUND")) {
            if (plane.getSpeed() + (50 / timeMultiplier) > 400) {
                plane.setSpeed(400);
            }
            plane.setSpeed(plane.getSpeed() + ((random.nextInt(10) + 50) / timeMultiplier));
        }

        if ( plane.getSpeed() <= 0){
            plane.setSpeed(0);
        }
    }
}
