package com.example.atc.service;

import com.example.atc.FlightGen.PlaneGen;
import com.example.atc.dao.DataAccessService;
import com.example.atc.model.Gate;
import com.example.atc.model.Runway;
import com.example.atc.model.Taxiway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;

public class AtcInitializationService implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public AtcInitializationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        // clears all rows

        final String sql_delete_gates = "DELETE FROM gate CASCADE;" ;
        jdbcTemplate.update(sql_delete_gates);

        final String sql_delete_taxiways = "DELETE FROM taxiway CASCADE;" ;
        jdbcTemplate.update(sql_delete_taxiways);

        final String sql_delete_runways = "DELETE FROM runway CASCADE;" ;
        jdbcTemplate.update(sql_delete_runways);

        final String sql_delete_planes = "DELETE FROM plane CASCADE;" ;
        jdbcTemplate.update(sql_delete_planes);


        // insert 100 planes into the database
        DataAccessService dataAccessService = new DataAccessService(jdbcTemplate);

        for (int i = 0; i < 50; i++)
        {
            PlaneGen planeGen = new PlaneGen();
            dataAccessService.insertPlane(planeGen);
        }

        // insert 16 gates
        for (int i = 1; i < 17; i++) {
            Gate gate = new Gate(i, "G" + i, null);
            dataAccessService.insertGate(gate);
        }

        // insert 2 physical runways (4 practical)
        Runway runway18L = new Runway(1, "18L", null);
        Runway runway36R = new Runway(2, "36R", null);
        Runway runway18R = new Runway(3, "18R", null);
        Runway runway36L = new Runway(4, "36L", null);

        dataAccessService.insertRunway(runway18L);
        dataAccessService.insertRunway(runway36R);
        dataAccessService.insertRunway(runway18R);
        dataAccessService.insertRunway(runway36L);


        // insert 4 taxiways
        Taxiway taxiway1 = new Taxiway(1, "A", null);
        Taxiway taxiway2 = new Taxiway(2, "B", null);
        Taxiway taxiway3 = new Taxiway(3, "C", null);
        Taxiway taxiway4 = new Taxiway(4, "D", null);

        dataAccessService.insertTaxiway(taxiway1);
        dataAccessService.insertTaxiway(taxiway2);
        dataAccessService.insertTaxiway(taxiway3);
        dataAccessService.insertTaxiway(taxiway4);

        /*
		// tests

		Plane controlPlane = new Plane(UUID.randomUUID(),
				"NA1234", "INBOUND", System.currentTimeMillis(),
				100, 100, 100, 100);
		dataAccessService.insertPlane(controlPlane);

		Plane controlPlane2 = new Plane(UUID.randomUUID(),
				"NA1235", "INBOUND", System.currentTimeMillis(),
				100, 100, 100, 100);
		dataAccessService.insertPlane(controlPlane2);

		Plane controlPlane3 = new Plane(UUID.randomUUID(),
				"N4321", "INBOUND", System.currentTimeMillis(),
				100, 100, 100, 100);
		dataAccessService.insertPlane(controlPlane3);
*/

		/*
		// update plane test
		controlPlane.setState("EMERGENCY");
		dataAccessService.updatePlaneById(controlPlane.getId(), controlPlane);
		*/

        // delete plane test
        //dataAccessService.deletePlaneById(controlPlane.getId());

/*
		// gate tests
		Gate gate = new Gate(0, "TEST", null);
		dataAccessService.insertGate(gate);
		gate.setPlane_id(controlPlane.getId());
		// update test
		dataAccessService.updateGateById(gate.getGate_id(), gate);


		// runway update test
		runway18L.setPlane_id(controlPlane2.getId());
		dataAccessService.updateRunwayById(runway18L.getRunway_id(), runway18L);


		// taxiway update test
		taxiway1.setPlane_id(controlPlane3.getId());
		dataAccessService.updateTaxiwayById(taxiway1.getTaxiway_id(), taxiway1);
*/

    }
}
