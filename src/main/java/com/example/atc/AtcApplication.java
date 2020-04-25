package com.example.atc;


import com.example.atc.FlightGen.PlaneGen;
import com.example.atc.dao.DataAccessService;
import com.example.atc.model.Gate;
import com.example.atc.model.Plane;
import com.example.atc.model.Runway;
import com.example.atc.model.Taxiway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

@SpringBootApplication
public class AtcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AtcApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;



	@Override
	public void run(String... args) throws Exception {





		// clears all rows
		final String sql_delete_planes = "DELETE FROM plane;" ;
		jdbcTemplate.update(sql_delete_planes);

		final String sql_delete_gates = "DELETE FROM gate;" ;
		jdbcTemplate.update(sql_delete_gates);

		final String sql_delete_runways = "DELETE FROM runway;" ;
		jdbcTemplate.update(sql_delete_runways);

		final String sql_delete_taxiways = "DELETE FROM taxiway;" ;
		jdbcTemplate.update(sql_delete_taxiways);


		// insert 100 planes into the database
		DataAccessService dataAccessService = new DataAccessService(jdbcTemplate);

		for (int i = 0; i < 100; i++)
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
		Plane controlPlane = new Plane(new UUID(0,0),
				"NA1234", "INBOUND", System.currentTimeMillis(),
				100, 100, 100, 100);
		dataAccessService.insertPlane(controlPlane);


		// update test
		controlPlane.setState("EMERGENCY");
		dataAccessService.updatePlaneById(controlPlane.getId(), controlPlane);
*/



	}
}
