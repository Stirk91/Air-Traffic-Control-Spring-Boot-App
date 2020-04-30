

package com.example.atc.api;

import com.example.atc.model.Plane;
import com.example.atc.model.Runway;
import com.example.atc.model.Taxiway;
import com.example.atc.service.PlaneService;
import com.example.atc.service.RunwayService;
import com.example.atc.service.TaxiwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

//http://localhost:8080/v1/taxiway
@RequestMapping("v1/taxiway")
@CrossOrigin(maxAge = 3600)
@RestController
public class TaxiwayController {

    private final TaxiwayService taxiwayService;

    @Autowired
    public TaxiwayController(TaxiwayService taxiwayService) {
        this.taxiwayService = taxiwayService;
    }

    @PostMapping
    public void addTaxiway(@Valid @NonNull @RequestBody Taxiway taxiway) {
        taxiwayService.addTaxiway(taxiway);
    }

    @GetMapping
    public List<Taxiway> getAllTaxiways() {
        return taxiwayService.getAllTaxiways();
    }

    @GetMapping(path = "{id}")
    public Taxiway getTaxiwayById(@PathVariable("id") int taxiway_id) {
        return taxiwayService.getTaxiwayById(taxiway_id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteTaxiwayById(@PathVariable("id") int taxiway_id) {
        taxiwayService.deleteTaxiway(taxiway_id);
    }

    @PutMapping(path = "{id}")
    public void updateTaxiwayById(@PathVariable("id") int taxiway_id, @Valid @NonNull @RequestBody Taxiway taxiwayToUpdate) {
        taxiwayService.updateTaxiway(taxiway_id, taxiwayToUpdate);
    }


}