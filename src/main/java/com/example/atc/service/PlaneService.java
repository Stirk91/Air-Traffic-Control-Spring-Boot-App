package com.example.atc.service;

import com.example.atc.dao.Dao;
import com.example.atc.dao.DataAccessService;
import com.example.atc.model.Plane;
import com.example.atc.model.PlaneExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaneService {

    private final Dao dao;

    @Autowired
    public PlaneService(@Qualifier("postgres") Dao dao) {
        this.dao = dao;
    }

    public int addPlane(Plane plane) {
        return dao.insertPlane(plane);
    }

    public List<Plane> getAllPlanes() {
        return dao.selectAllPlanes();
    }

    public List<Plane> getAllPlanesGlobal() {
        return dao.selectAllPlanesGlobal();
    }

    public Optional<Plane> getPlaneById(UUID id) {
        return dao.selectPlaneById(id);
    }

    public int deletePlane(UUID id) {
        return dao.deletePlaneById(id);
    }

    public int updatePlane(UUID id, Plane newPlane) {
        return dao.updatePlaneById(id, newPlane);
    }

    public List<PlaneExtended> getAllPlanesWithRunwayTaxiwayGate()  {return dao.selectAllPlanesWithRunwayTaxiwayGate();
    }
}