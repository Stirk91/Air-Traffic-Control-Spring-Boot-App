package com.example.atc.dao;

import com.example.atc.model.Plane;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("tempDao")
public class TempPlaneDataAccessService implements PlaneDao {

    private static List<Plane> database = new ArrayList<>();

    @Override
    public int insertPlane(UUID id, Plane plane) {
        database.add(new Plane(id, plane.getRegistration()));
        return 1;
    }

    @Override
    public List<Plane> selectAllPlanes() {
        return database;
    }

    @Override
    public Optional<Plane> selectPlaneById(UUID id) {
        return database.stream()
                .filter(plane -> plane.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePlaneById(UUID id) {
        Optional<Plane> planeMaybe = selectPlaneById(id);
        if (planeMaybe.isEmpty()){
            return 0;
        }
        else {
            database.remove(planeMaybe.get());
            return 1;
        }
    }

    @Override
    public int updatePlaneById(UUID id, Plane planeUpdate) {
        return selectPlaneById(id)
                .map(plane -> {
                    int indexOfPlaneToUpdate = database.indexOf(plane);
                    if (indexOfPlaneToUpdate >= 0) {
                        database.set(indexOfPlaneToUpdate, new Plane(id, planeUpdate.getRegistration()));
                        return 1;
                    }
                    else {
                        return 0;
                    }
                })
                .orElse(0);

    }
}
