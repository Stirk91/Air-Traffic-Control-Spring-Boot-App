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

import java.util.List;


public class AtcControlService implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public AtcControlService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        DataAccessService dataAccessService = new DataAccessService(jdbcTemplate);
        List<Plane> atcPlanes = dataAccessService.selectAllPlanes();


        // example: reducing speed by 10 per turn
        for (int i = 0; i < atcPlanes.size(); i++) {
            Plane plane = atcPlanes.get(i);

            int planeSpeed = plane.getSpeed();

            plane.setSpeed(planeSpeed - 10);

            dataAccessService.updatePlaneById(plane.getId(), plane);
        }


        /*
        // test
        Plane update_plane = new Plane();
        update_plane = atcPlanes.get(0);
        update_plane.setState("EMERGENCY");
        dataAccessService.updatePlaneById(atcPlanes.get(0).getId(), update_plane);
        */






    }


    // this should be called from the AtcApplication and run in a loop

    /*
    ONE TURN / TICK = x minutes; // we can change this if this too slow

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
