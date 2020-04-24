package com.example.atc.service;

import com.example.atc.dao.Dao;
import com.example.atc.model.Taxiway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaxiwayService {

    private final Dao dao;

    @Autowired
    public TaxiwayService(@Qualifier("database") Dao dao) {
        this.dao = dao;
    }

    public int addTaxiway(Taxiway taxiway) {
        return dao.insertTaxiway(taxiway);
    }

    public List<Taxiway> getAllTaxiways() {
        return dao.selectAllTaxiways();
    }

    public Optional<Taxiway> getTaxiwayById(int taxiway_id) {
        return dao.selectTaxiwayById(taxiway_id);
    }

    public int deleteTaxiway(int taxiway_id) {
        return dao.deleteTaxiwayById(taxiway_id);
    }

    public int updateTaxiway(int taxiway_id, Taxiway newTaxiway) {
        return dao.updateTaxiwayById(taxiway_id, newTaxiway);
    }

}

