package com.example.atc.service;

import com.example.atc.dao.Dao;
import com.example.atc.model.Plane;
import com.example.atc.model.Runway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RunwayService {

    private final Dao dao;

    @Autowired
    public RunwayService(@Qualifier("postgres") Dao dao) {
        this.dao = dao;
    }

    public int addRunway(Runway runway) {
        return dao.insertRunway(runway);
    }

    public List<Runway> getAllRunways() {
        return dao.selectAllRunways();
    }

    public Optional<Runway> getRunwayById(int runway_id) {
        return dao.selectRunwayById(runway_id);
    }

    public int deleteRunway(int runway_id) {
        return dao.deleteRunwayById(runway_id);
    }

    public int updateRunway(int runway_id, Runway newRunway) {
        return dao.updateRunwayById(runway_id, newRunway);
    }

}

