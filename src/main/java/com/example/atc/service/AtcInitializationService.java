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

        final String sql_delete_runway_designations = "DELETE FROM Runway_RunwayDesignation CASCADE;" ;
        jdbcTemplate.update(sql_delete_runway_designations);

        final String sql_delete_runways = "DELETE FROM runway CASCADE;" ;
        jdbcTemplate.update(sql_delete_runways);

        final String sql_delete_planes = "DELETE FROM plane CASCADE;" ;
        jdbcTemplate.update(sql_delete_planes);



        // insert n planes into the database
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

        // we don't actually use this in the logic right now
        final String insertRunwayDesignations =
                "INSERT INTO Runway_RunwayDesignation " +
                "(runway_id, runway_designation)" +
                "VALUES " +
                "(1, '18L'), " +
                "(1, '36R'), " +
                "(2, '18R'), " +
                "(2, '36L')";

                jdbcTemplate.update(insertRunwayDesignations);

    }
}
