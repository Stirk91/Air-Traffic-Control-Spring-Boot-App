package com.example.atc.service;
import com.example.atc.dao.Dao;
import com.example.atc.model.Gate;
import com.example.atc.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class GateService {

    private final Dao dao;

    @Autowired
    public GateService(@Qualifier("database") Dao dao) {
        this.dao = dao;
    }

    public int addGate(Gate gate) {
        return dao.insertGate(gate);
    }

    public List<Gate> getAllGates() {
        return dao.selectAllGates();
    }

    public Optional<Gate> getGateById(int gate_id) {
        return dao.selectGateById(gate_id);
    }

    public int deleteGate(int gate_id) {
        return dao.deleteGateById(gate_id);
    }

    public int updateGate(int id, Gate newGate) {
        return dao.updateGateById(id, newGate);
    }

}





