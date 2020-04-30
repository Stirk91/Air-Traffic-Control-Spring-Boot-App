package com.example.atc.dao;

import com.example.atc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Dao {

    // Planes
    int insertPlane(Plane plane);
    List<Plane> selectAllPlanes();
    Optional<Plane> selectPlaneById(UUID id);
    int deletePlaneById(UUID id);
    int updatePlaneById(UUID id, Plane plane);


    // Gates
    int insertGate(Gate gate);
    List<Gate> selectAllGates();
    Optional<Gate> selectGateById(int gate_id);
    int updateGateById(int gate_id, Gate gate);
    int deleteGateById(int gate_id);
    int updateGateByPlaneId(UUID plane_id);

    // Runways
    int insertRunway(Runway runway);
    List<Runway> selectAllRunways();
    Optional<Runway> selectRunwayById(int runway_id);
    int deleteRunwayById(int runway_id);
    int updateRunwayById(int runway_id, Runway newRunway);
    int updateRunwayByPlaneId(UUID plane_id);
    Runway selectRunwayByPlaneId(UUID plane_id);
    int updateRunwayByName(String runway_name, UUID plane_id);


    // Taxiways
    int insertTaxiway(Taxiway taxiway);
    List<Taxiway> selectAllTaxiways();
    Optional<Taxiway> selectTaxiwayById(int taxiway_id);
    int deleteTaxiwayById(int taxiway_id);
    int updateTaxiwayById(int taxiway_id, Taxiway newTaxiway);
    int updateTaxiwayByPlaneId(UUID plane_id);

    // Combined
    List<PlaneExtended> selectAllPlanesWithRunwayTaxiwayGate();
}




