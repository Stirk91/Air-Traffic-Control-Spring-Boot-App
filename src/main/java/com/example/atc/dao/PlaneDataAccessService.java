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
        return 0;
    }

    @Override
    public List<Plane> selectAllPlanes() {
        final String sql = "SELECT plane_id, tail_number FROM plane";

        List<Plane> planes = jdbcTemplate.query(
                // pass sql query as string
                sql,
                // row mapper (lambda)
                (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("plane_id"));
            String registration = resultSet.getString("tail__number");
            return new Plane(id, registration);
        });
        return planes;
    }

    @Override
    public Optional<Plane> selectPlaneById(UUID id) {
        final String sql = "SELECT plane_id, tail_number FROM plane WHERE plane_id = ?";

        Plane plane = jdbcTemplate.queryForObject(
                // pass sql query as string
                sql,
                // new object array with query (id, ...)
                new Object[]{id},
                // row mapper (lambda)
                (resultSet, i) -> {
            UUID planeId = UUID.fromString(resultSet.getString("plane_id"));
            String registration = resultSet.getString("tail__number");
            return new Plane(planeId, registration);
                });
        return Optional.ofNullable(plane);
    }

    @Override
    public int deletePlaneById(UUID id) {
        return 0;
    }

    @Override
    public int updatePlaneById(UUID id, Plane plane) {
        return 0;
    }
}
