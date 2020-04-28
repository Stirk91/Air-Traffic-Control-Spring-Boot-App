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


        // PLANE LOGIC
        for (int i = 0; i < atcPlanes.size(); i++) {

            Plane plane = atcPlanes.get(i);


            if (plane.getState().equals("TAKEOFF")) {
                System.out.println("Plane " + plane.getTail_number() + "at line 104");
                dataAccessService.updateRunwayByPlaneId(plane.getId());
                plane.setState("OUTBOUND");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " is outbound");
            }


            else if (plane.getState().equals("TAXIING_FROM_GATE") && runwaysAvailable.size() != 0 &&
                    ( System.currentTimeMillis() - plane.getLast_action() > ( 25000 / timeMultiplier))) {
                System.out.println("Plane " + plane.getTail_number() + "at line 115");
                dataAccessService.updateTaxiwayByPlaneId(plane.getId());
                runwaysAvailable.get(0)[0].setPlane_id(plane.getId()); // assign plane to first available runway
                dataAccessService.updateRunwayById(runwaysAvailable.get(0)[0].getRunway_id(), runwaysAvailable.get(0)[0]);
                runwaysAvailable.remove(0); // remove runway from runwaysAvailable
                plane.setState("TAKEOFF");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " is taking off");
            }


            else if  (plane.getState().equals("ARRIVED") && taxiwaysAvailable.size() != 0 &&
                    ( System.currentTimeMillis() - plane.getLast_action() > ( 60000 / timeMultiplier))) {
                System.out.println("Plane " + plane.getTail_number() + "at line 129");
                dataAccessService.updateGateByPlaneId(plane.getId());
                taxiwaysAvailable.get(0).setPlane_id(plane.getId()); // assign plane to first available taxiway
                dataAccessService.updateTaxiwayById(taxiwaysAvailable.get(0).getTaxiway_id(), taxiwaysAvailable.get(0));
                plane.setState("TAXIING_FROM_GATE");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " is taxiing from gate");
            }


            else if  (plane.getState().equals("TAXIING_TO_GATE") && gatesAvailable.size() != 0 &&
                    ( System.currentTimeMillis() - plane.getLast_action() > ( 25000 / timeMultiplier))) {
                System.out.println("Plane " + plane.getTail_number() + "at line 142");
                dataAccessService.updateTaxiwayByPlaneId(plane.getId());
                gatesAvailable.get(0).setPlane_id(plane.getId()); // assign plane to first available taxiway
                dataAccessService.updateGateById(gatesAvailable.get(0).getGate_id(), gatesAvailable.get(0));
                plane.setState("ARRIVED");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " has arrived");
            }


            else if  (plane.getState().equals("LANDED") && taxiwaysAvailable.size() != 0) {
                System.out.println("Plane " + plane.getTail_number() + "at line 153");
                dataAccessService.updateRunwayByPlaneId(plane.getId());
                System.out.println("Plane " + plane.getTail_number() + "at line 155");
                taxiwaysAvailable.get(0).setPlane_id(plane.getId()); // assign plane to first available taxiway
                System.out.println("Plane " + plane.getTail_number() + "at line 157");
                dataAccessService.updateTaxiwayById(taxiwaysAvailable.get(0).getTaxiway_id(), taxiwaysAvailable.get(0));
                System.out.println("Plane " + plane.getTail_number() + "at line 159");
                plane.setState("TAXIING_TO_GATE");
                System.out.println("Plane " + plane.getTail_number() + "at line 161");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " is taxiing to gate");
            }


            else if  (plane.getState().equals("FINAL") && plane.getDistance() == 0) {
                System.out.println("Plane " + plane.getTail_number() + "at line 164");
                plane.setState("LANDED");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " has landed");
            }


                // TODO needs to consider plane EMERGENCY (right now in PlaneGen there's 0% possibility of EMERGENCY
            else if   (plane.getState().equals("INBOUND")  &&
                    (plane.getDistance() < 21120) && (runwaysAvailable.size() != 0) && (gatesAvailable.size() != 0)) {
                System.out.println("Plane " + plane.getTail_number() + "at line 174");
                plane.setState("FINAL");
                updateLast_action(plane);

                // TODO needs to consider plane heading when assigning runway
                runwaysAvailable.get(0)[0].setPlane_id(plane.getId()); // assign plane to first available runway

                dataAccessService.updateRunwayById(runwaysAvailable.get(0)[0].getRunway_id(), runwaysAvailable.get(0)[0]);
                runwaysAvailable.remove(0); // remove runway from runwaysAvailable
                System.out.println("Plane " + plane.getTail_number() + " is on final");
            }


            else if (plane.getState().equals("INBOUND") &&
                    (plane.getDistance() < 21120) && (runwaysAvailable.size() == 0 || taxiwaysAvailable.size() == 0) ||
                    gatesAvailable.size() == 0) {
                System.out.println("Plane " + plane.getTail_number() + "at line 189");
                plane.setState("HOLDING");
                updateLast_action(plane);
                System.out.println("Plane " + plane.getTail_number() + " switched from inbound to holding");
            }


            else if (plane.getState().equals("HOLDING")  &&
                    (plane.getDistance() < 21120) && runwaysAvailable.size() != 0 && taxiwaysAvailable.size() != 0 &&
                    gatesAvailable.size() != 0) {
                System.out.println("Plane " + plane.getTail_number() + "at line 198");
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



    // TODO: Each update functions needs to account for the plane state

    private void updateLast_action(Plane plane) {
        plane.setLast_action(System.currentTimeMillis());
    }


    private void updateHeading(Plane plane) {

        // TODO fix this is broken since they over correct

        //int turnRate = (2 * 60) / timeMultiplier;

    }

    private void updateDistance(Plane plane) {

        plane.setDistance(plane.getDistance() - ((plane.getSpeed() * 60) / timeMultiplier));

        if ( plane.getDistance() <= 0){
            plane.setDistance(0);
        }
    }

    private void updateAltitude(Plane plane) {


        if (plane.getState().equals("INBOUND")) {
            plane.setAltitude(plane.getAltitude() - 10);
        }


        // TODO fix planes hitting 0 before "LANDED"
        if (plane.getState().equals("FINAL")) {
            plane.setAltitude(plane.getAltitude() - ((400 * 60) / timeMultiplier)); // 400 feet per minute
        }

        if (plane.getState().equals("LANDED")) {
            plane.setAltitude(0);
        }

        if ( plane.getAltitude() <= 0){
            plane.setAltitude(0);
        }
    }

    private void updateSpeed(Plane plane) {


        if (plane.getState().equals("ARRIVED")) {
            plane.setSpeed(0);
        }

        if (plane.getState().equals("TAXIING")) {
            plane.setSpeed(20);
        }

        if (plane.getState().equals("FINAL")) {
            plane.setSpeed(150);
        }

        if (plane.getState().equals("INBOUND")) {
            if (plane.getSpeed() - 10 < 200) {
                plane.setSpeed(200);
            }
            plane.setSpeed(plane.getSpeed() - 10);

        }

        if ( plane.getSpeed() <= 0){
            plane.setSpeed(0);
        }

    }


    /*

    more rules
    https://docs.google.com/document/d/1i3DM7EDb8B6OqLvqZisP2DlAeVT4OQ36tWkvdWt0E5M/edit

    All planes are generated in the air not on the ground
    All planes are added to a queue based on distance

    Priority queues as follows

    States:
    FINAL means the planes is preparing to land. Only one plane can be FINAL at a time per runway (we have two runways)
    HOLDING means the plane has had to wait for other planes to land, so they should go before INBOUND
    EMERGENCY means the plane must jump the queue of all other INBOUND planes but not planes on FINAL approach
    INBOUND means the plane is preparing to land and is added to the runway queue based on distance

    If a plane is FINAL: speed should be 150-165; altitude ~500; heading aligned with runway

    If a plane is DEPARTING: speed should be 180-200 mph; heading aligned aligned with runway

    EVERY TURN/TICK the planes should travel x distance based on their existing speed; lower altitude linearly


    After LANDED it takes 5 minutes to reach the gate

    After LEAVING GATE it takes 5 minutes to reach the runwway

    Planes can only take off when no planes are FINAL

    Planes spend 45 minutes at a gate

    There are 16 gates. So if all gates are occupied, only 4 more planes can land. All others must be set to HOLDING

    There are 2 runways (each runway needs 2 queues, one for each direction)

    For the sake of ease, let's say the taxiways can hold any number of planes.




 */


}
