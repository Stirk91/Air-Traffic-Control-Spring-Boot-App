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

        final String sql_delete_taxiway_gate = "DELETE FROM Taxiway_Gate CASCADE;" ;
        jdbcTemplate.update(sql_delete_taxiway_gate);

        final String sql_delete_gates = "DELETE FROM gate CASCADE;" ;
        jdbcTemplate.update(sql_delete_gates);

        final String sql_delete_runway_taxiway = "DELETE FROM Runway_Taxiway CASCADE;" ;
        jdbcTemplate.update(sql_delete_runway_taxiway);

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

        final String insertRunwayTaxiway =
                "INSERT INTO Runway_Taxiway " +
                        "(runway_id, taxiway_id)" +
                        "VALUES " +
                        "(1, 1), " +
                        "(1, 2), " +
                        "(1, 3), " +
                        "(1, 4), " +
                        "(2, 1), " +
                        "(2, 2), " +
                        "(2, 3), " +
                        "(2, 4)";

        jdbcTemplate.update(insertRunwayTaxiway);

        // need to rewrite this programmatically
        final String insertTaxiwayGate =
                "INSERT INTO Taxiway_Gate " +
                        "(taxiway_id, gate_id)" +
                        "VALUES " +
                        "(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8)," +
                        "(1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14), (1, 15), (1, 16)," +
                        "(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8)," +
                        "(2, 9), (2, 10), (2, 11), (2, 12), (2, 13), (2, 14), (2, 15), (2, 16)," +
                        "(3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8)," +
                        "(3, 9), (3, 10), (3, 11), (3, 12), (3, 13), (3, 14), (3, 15), (3, 16)," +
                        "(4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7), (4, 8)," +
                        "(4, 9), (4, 10), (4, 11), (4, 12), (4, 13), (4, 14), (4, 15), (4, 16)";

        jdbcTemplate.update(insertTaxiwayGate);











    }
}