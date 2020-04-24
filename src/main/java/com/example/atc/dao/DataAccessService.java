package com.example.atc.dao;

import com.example.atc.model.Gate;
import com.example.atc.model.Plane;
import com.example.atc.model.Runway;
import com.example.atc.model.Taxiway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("database")
public class DataAccessService implements Dao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Planes

    @Override
    public int insertPlane(UUID id, Plane plane) {
        final String sql = "INSERT INTO plane " +
                "(plane_id, tail_number, state, last_action, distance, altitude, speed, heading)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                id,
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
            return new Plane(id, tail_number, state, last_action, distance, altitude, speed, heading);
                });
        return Optional.ofNullable(plane);
    }

    @Override
    public int deletePlaneById(UUID id) {
        return 0;
    }

    @Override
    public int updatePlaneById(UUID id, Plane plane) {
        return 0; }


    // Gates

    @Override
    public int insertGate(Gate gate) {
        return 0;
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
                        plane_id = new UUID(0,0); // assigns nil uuid
                    }
                    else {
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
        return 0;
    }

    @Override
    public int deleteGateById(int gate_id) {
        return 0;
    }


    // Runways

    @Override
    public int insertRunway(Runway runway) {
        return 0;
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
                        plane_id = new UUID(0,0); // assigns nil uuid
                    }
                    else {
                        plane_id = UUID.fromString(resultSet.getString("plane_id"));
                    }
                    return new Runway(runway_id, runway_name, plane_id);
                });
        return runways;
    }

    @Override
    public Optional<Runway> selectRunwayById(int runway_id) {
        return Optional.empty();
    }

    @Override
    public int deleteRunwayById(int runway_id) {
        return 0;
    }

    @Override
    public int updateRunwayById(int runway_id, Runway newRunway) {
        return 0;
    }

    @Override
    public int insertTaxiway(Taxiway taxiway) {
        return 0;
    }


    @Override
    public List<Taxiway> selectAllTaxiways() {
        final String sql = "SELECT * FROM runway";

        List<Taxiway> taxiways = jdbcTemplate.query(
                // pass sql query as string
                sql,
                // row mapper (lambda)
                (resultSet, i) -> {
                    int taxiway_id = resultSet.getInt("runway_id");
                    String taxiway_name = resultSet.getString("runway_name");
                    UUID plane_id;
                    if (resultSet.getString("plane_id") == null) {
                        plane_id = new UUID(0,0); // assigns nil uuid
                    }
                    else {
                        plane_id = UUID.fromString(resultSet.getString("plane_id"));
                    }
                    return new Taxiway(taxiway_id, taxiway_name, plane_id);
                });
        return taxiways;
    }

    @Override
    public Optional<Taxiway> selectTaxiwayById(int taxiway_id) {
        return Optional.empty();
    }

    @Override
    public int deleteTaxiwayById(int taxiway_id) {
        return 0;
    }

    @Override
    public int updateTaxiwayById(int taxiway_id, Taxiway newTaxiway) {
        return 0;
    }
}
