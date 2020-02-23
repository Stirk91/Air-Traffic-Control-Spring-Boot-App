package com.example.atc.dao;

import com.example.atc.model.Plane;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaneDao {

    int insertPlane(UUID id, Plane plane);

    default int insertPlane(Plane plane) {
        UUID id = UUID.randomUUID();
        return insertPlane(id, plane);
    }

    List<Plane> selectAllPlanes();

    Optional<Plane> selectPlaneById(UUID id);

    int deletePlaneById(UUID id);

    int updatePlaneById(UUID id, Plane plane);

}
