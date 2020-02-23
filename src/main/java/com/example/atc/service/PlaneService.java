package com.example.atc.service;

import com.example.atc.dao.PlaneDao;
import com.example.atc.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaneService {

    private final PlaneDao planeDao;

    @Autowired
    public PlaneService(@Qualifier("database") PlaneDao planeDao) {
        this.planeDao = planeDao;
    }

    public int addPlane(Plane plane) {
        return planeDao.insertPlane(plane);
    }

    public List<Plane> getAllPlanes() {
        return planeDao.selectAllPlanes();
    }

    public Optional<Plane> getPlaneById(UUID id) {
        return planeDao.selectPlaneById(id);
    }

    public int deletePlane(UUID id) {
        return planeDao.deletePlaneById(id);
    }

    public int updatePlane(UUID id, Plane newPlane) {
        return planeDao.updatePlaneById(id, newPlane);
    }

}

