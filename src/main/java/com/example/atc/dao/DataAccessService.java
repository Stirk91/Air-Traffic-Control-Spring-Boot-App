package com.example.atc.dao;

import com.example.atc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class DataAccessService implements Dao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Planes

    @Override
    public int insertPlane(Plane plane) {
        final String sql = "INSERT INTO plane " +
                "(plane_id, tail_number, state, last_action, distance, altitude, speed, heading)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                plane.getId(),
                plane.getTail_number(),
                plane.getState(),
                plane.getLast_action(),
                plane.getDistance(),
                plane.getAltitude(),
                plane.getSpeed(),
                plane.getHeading()
        );
    }

    @Override
    public List<Plane> selectAllPlanes() {
        final String sql = "SELECT * FROM plane";

        List<Plane> planes = jdbcTemplate.query(
                // pass sql query as string
                sql,
                // row mapper (lambda)
                (resultSet, i) -> {
                    UUID id = UUID.fromString(resultSet.getString("plane_id"));
                    String tail_number = resultSet.getString("tail_number");
                    String state = resultSet.getString("state");
                    long last_action = resultSet.getLong("last_action");
                    int distance = resultSet.getInt("distance");
                    int altitude = resultSet.getInt("altitude");
                    int speed = resultSet.getInt("speed");
                    int heading = resultSet.getInt("heading");
                    return new Plane(id, tail_number, state, last_action, distance, altitude, speed, heading);
                });
        return planes;
    }

    @Override
    public Optional<Plane> selectPlaneById(UUID id) {
        final String sql = "SELECT * FROM plane WHERE plane_id = ?";

        Plane plane = jdbcTemplate.queryForObject(
                // pass sql query as string
                sql,
                // new object array with query (id, ...)
                new Object[]{id},
                // row mapper (lambda)
                (resultSet, i) -> {
                    UUID planeId = UUID.fromString(resultSet.getString("plane_id"));
                    String tail_number = resultSet.getString("tail_number");
                    String state = resultSet.getString("state");
                    long last_action = resultSet.getLong("last_action");
                    int distance = resultSet.getInt("distance");
                    int altitude = resultSet.getInt("altitude");
                    int speed = resultSet.getInt("speed");
                    int heading = resultSet.getInt("heading");
                    return new Plane(planeId, tail_number, state, last_action, distance, altitude, speed, heading);
                });
        return Optional.ofNullable(plane);
    }

    @Override
    public int deletePlaneById(UUID id) {
        final String sql = "DELETE from plane " +
                "WHERE plane_id =?";

        // refer to https://www.tutorialspoint.com/springjdbc/springjdbc_delete_query.htm

        return jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public int updatePlaneById(UUID id, Plane plane) {
        final String sql = "UPDATE plane " +
                "SET tail_number =?, state =?, last_action =?, distance =?, altitude =?, speed =?, heading =? " +
                "WHERE plane_id =?";

        // refer to https://www.tutorialspoint.com/springjdbc/springjdbc_update_query.htm

        return jdbcTemplate.update(
                sql,
                plane.getTail_number(),
                plane.getState(),
                plane.getLast_action(),
                plane.getDistance(),
                plane.getAltitude(),
                plane.getSpeed(),
                plane.getHeading(),
                id
        );
    }


    // Gates

    public int insertGate(Gate gate) {
        final String sql = "INSERT INTO gate " +
                "(gate_id, gate_name, plane_id)" +
                "VALUES (?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                gate.getGate_id(),
                gate.getGate_name(),
                gate.getPlane_id()
        );
    }

    @Override
    public List<Gate> selectAllGates() {
        final String sql = "SELECT * FROM gate";

        List<Gate> gates = jdbcTemplate.query(
                // pass sql query as string
                sql,
                // row mapper (lambda)
                (resultSet, i) -> {
                    int gate_id = resultSet.getInt("gate_id");
                    String gate_name = resultSet.getString("gate_name");
                    UUID plane_id;
                    if (resultSet.getString("plane_id") == null) {
                        plane_id = new UUID(0, 0); // assigns nil uuid
                    } else {
                        plane_id = UUID.fromString(resultSet.getString("plane_id"));
                    }
                    return new Gate(gate_id, gate_name, plane_id);
                });
        return gates;
    }

    public Optional<Gate> selectGateById(int id) {
        final String sql = "SELECT * FROM gate WHERE gate_id = ?";

        Gate gate = jdbcTemplate.queryForObject(
                // pass sql query as string
                sql,
                // new object array with query (id, ...)
                new Object[]{id},
                // row mapper (lambda)
                (resultSet, i) -> {
                    int gate_id = resultSet.getInt("gate_id");
                    String gate_name = resultSet.getString("gate_name");
                    UUID plane_id = UUID.fromString(resultSet.getString("plane_id"));
                    return new Gate(gate_id, gate_name, plane_id);
                });
        return Optional.ofNullable(gate);
    }

    @Override
    public int updateGateById(int id, Gate gate) {
        final String sql = "UPDATE gate " +
                "SET plane_id =?" +
                "WHERE gate_id =?";

        return jdbcTemplate.update(
                sql,
                gate.getPlane_id(),
                id
        );
    }

    @Override
    public int updateGateByPlaneId(UUID plane_id) {
        final String sql = "UPDATE gate " +
                "SET plane_id =?" +
                "WHERE plane_id =?";

        return jdbcTemplate.update(
                sql,
                null,
                plane_id
        );
    }

    @Override
    public int deleteGateById(int gate_id) {
        return 0;
    }


    // Runways

    @Override
    public int insertRunway(Runway runway) {
        final String sql = "INSERT INTO runway " +
                "(runway_id, runway_name, plane_id)" +
                "VALUES (?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                runway.getRunway_id(),
                runway.getRunway_name(),
                runway.getPlane_id()
        );
    }

    @Override
    public List<Runway> selectAllRunways() {
        final String sql = "SELECT * FROM runway";

        List<Runway> runways = jdbcTemplate.query(
                // pass sql query as string
                sql,
                // row mapper (lambda)
                (resultSet, i) -> {
                    int runway_id = resultSet.getInt("runway_id");
                    String runway_name = resultSet.getString("runway_name");
                    UUID plane_id;
                    if (resultSet.getString("plane_id") == null) {
                        plane_id = new UUID(0, 0); // assigns nil uuid
                    } else {
                        plane_id = UUID.fromString(resultSet.getString("plane_id"));
                    }
                    return new Runway(runway_id, runway_name, plane_id);
                });
        return runways;
    }

    @Override
    public Optional<Runway> selectRunwayById(int id) {
        final String sql = "SELECT * FROM runway WHERE runway_id = ?";

        Runway runway = jdbcTemplate.queryForObject(
                // pass sql query as string
                sql,
                // new object array with query (id, ...)
                new Object[]{id},
                // row mapper (lambda)
                (resultSet, i) -> {
                    int runway_id = resultSet.getInt("runway_id");
                    String runway_name = resultSet.getString("runway_name");
                    UUID plane_id = UUID.fromString(resultSet.getString("plane_id"));
                    return new Runway(runway_id, runway_name, plane_id);
                });
        return Optional.ofNullable(runway);
    }

    public Runway selectRunwayByPlaneId(UUID plane_id) {
        final String sql = "SELECT * FROM runway WHERE plane_id = ?";

        Runway runway = jdbcTemplate.queryForObject(
                // pass sql query as string
                sql,
                // new object array with query (id, ...)
                new Object[]{plane_id},
                // row mapper (lambda)
                (resultSet, i) -> {
                    int runway_id = resultSet.getInt("runway_id");
                    String runway_name = resultSet.getString("runway_name");
                    UUID planeId = UUID.fromString(resultSet.getString("plane_id"));
                    return new Runway(runway_id, runway_name, planeId);
                });
        return runway;
    }


    @Override
    public int deleteRunwayById(int runway_id) {
        return 0;
    }

    @Override
    public int updateRunwayById(int runway_id, Runway runway) {
        final String sql = "UPDATE runway " +
                "SET plane_id =?" +
                "WHERE runway_id =?";

        return jdbcTemplate.update(
                sql,
                runway.getPlane_id(),
                runway_id
        );
    }

    @Override
    public int updateRunwayByName(String runway_name, UUID plane_id) {
        final String sql = "UPDATE runway " +
                "SET plane_id =?" +
                "WHERE runway_name =?";

        return jdbcTemplate.update(
                sql,
                plane_id,
                runway_name
        );
    }

    @Override
    public int updateRunwayByPlaneId(UUID plane_id) {
        final String sql = "UPDATE runway " +
                "SET plane_id =?" +
                "WHERE plane_id =?";

        return jdbcTemplate.update(
                sql,
                null,
                plane_id
        );
    }

    @Override
    public int insertTaxiway(Taxiway taxiway) {
        final String sql = "INSERT INTO taxiway " +
                "(taxiway_id, taxiway_name, plane_id)" +
                "VALUES (?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                taxiway.getTaxiway_id(),
                taxiway.getTaxiway_name(),
                taxiway.getPlane_id()
        );
    }

    @Override
    public List<Taxiway> selectAllTaxiways() {
        final String sql = "SELECT * FROM taxiway";

        List<Taxiway> taxiways = jdbcTemplate.query(
                // pass sql query as string
                sql,
                // row mapper (lambda)
                (resultSet, i) -> {
                    int taxiway_id = resultSet.getInt("taxiway_id");
                    String taxiway_name = resultSet.getString("taxiway_name");
                    UUID plane_id;
                    if (resultSet.getString("plane_id") == null) {
                        plane_id = new UUID(0, 0); // assigns nil uuid
                    } else {
                        plane_id = UUID.fromString(resultSet.getString("plane_id"));
                    }
                    return new Taxiway(taxiway_id, taxiway_name, plane_id);
                });
        return taxiways;
    }

    @Override
    public Optional<Taxiway> selectTaxiwayById(int id) {
        final String sql = "SELECT * FROM taxiway WHERE taxiway_id = ?";

        Taxiway taxiway = jdbcTemplate.queryForObject(
                // pass sql query as string
                sql,
                // new object array with query (id, ...)
                new Object[]{id},
                // row mapper (lambda)
                (resultSet, i) -> {
                    int taxiway_id = resultSet.getInt("taxiway_id");
                    String taxiway_name = resultSet.getString("taxiway_name");
                    UUID plane_id = UUID.fromString(resultSet.getString("plane_id"));
                    return new Taxiway(taxiway_id, taxiway_name, plane_id);
                });
        return Optional.ofNullable(taxiway);
    }

    @Override
    public int deleteTaxiwayById(int taxiway_id) {
        return 0;
    }

    @Override
    public int updateTaxiwayById(int taxiway_id, Taxiway taxiway) {
        final String sql = "UPDATE taxiway " +
                "SET plane_id =?" +
                "WHERE taxiway_id =?";

        return jdbcTemplate.update(
                sql,
                taxiway.getPlane_id(),
                taxiway_id
        );
    }

    @Override
    public int updateTaxiwayByPlaneId(UUID plane_id) {
        final String sql = "UPDATE taxiway " +
                "SET plane_id =?" +
                "WHERE plane_id =?";

        return jdbcTemplate.update(
                sql,
                null,
                plane_id
        );
    }

    @Override
    public List<PlaneExtended> selectAllPlanesWithRunwayTaxiwayGate() {

        List<Plane> planes = selectAllPlanes();
        List<PlaneExtended> planesExtended = new ArrayList<PlaneExtended>();



        for (int i = 0; i < planes.size(); i++) {
            PlaneExtended ePlane = new PlaneExtended();

            // convert Planes to ePlanes
            ePlane.setId(planes.get(i).getId());
            ePlane.setTail_number(planes.get(i).getTail_number());
            ePlane.setState(planes.get(i).getState());
            ePlane.setLast_action(planes.get(i).getLast_action());
            ePlane.setDistance(planes.get(i).getDistance());
            ePlane.setAltitude(planes.get(i).getAltitude());
            ePlane.setSpeed(planes.get(i).getSpeed());
            ePlane.setHeading(planes.get(i).getHeading());

            String sqlSelectRunway = "SELECT runway_name " +
                            "FROM runway " +
                            "WHERE plane_id =? ";

            // have to use string bc jdbc will error if queryForObject returns anything except 1 object
            List<String> runway_name = jdbcTemplate.query(
                    sqlSelectRunway,
                    new Object[]{planes.get(i).getId()},
                    (resultSet, j) -> {
                        String runwayName = resultSet.getString("runway_name");
                        return runwayName;
                    });

            if (runway_name.size() == 1) {
                ePlane.setRunway_name(runway_name.get(0));
            }
            else {
                ePlane.setRunway_name("");
            }

            String sqlSelectTaxiway = "SELECT taxiway_name " +
                    "FROM taxiway " +
                    "WHERE plane_id =? ";

            List<String> taxiway_name = jdbcTemplate.query(
                    sqlSelectTaxiway,
                    new Object[]{planes.get(i).getId()},
                    (resultSet, j) -> {
                        String taxiwayName = resultSet.getString("taxiway_name");
                        return taxiwayName;
                    });

            if (taxiway_name.size() == 1) {
                ePlane.setTaxiway_name(taxiway_name.get(0));
            }
            else {
                ePlane.setTaxiway_name("");
            }

            String sqlSelectGate = "SELECT gate_name " +
                    "FROM gate " +
                    "WHERE plane_id =? ";

            List<String> gate_name = jdbcTemplate.query(
                    sqlSelectGate,
                    new Object[]{planes.get(i).getId()},
                    (resultSet, j) -> {
                        String gateName = resultSet.getString("gate_name");
                        return gateName;
                    });

            if (gate_name.size() == 1) {
                ePlane.setGate_name(gate_name.get(0));
            }
            else {
                ePlane.setGate_name("");
            }




            planesExtended.add(ePlane);
        }
        return planesExtended;
    }
}
