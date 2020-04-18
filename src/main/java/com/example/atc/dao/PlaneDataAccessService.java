package com.example.atc.dao;

import com.example.atc.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("database")
public class PlaneDataAccessService implements PlaneDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlaneDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



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
    public int updatePlaneById(UUID id, Plane plane) { return 0; }
}
