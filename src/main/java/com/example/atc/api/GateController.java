package com.example.atc.api;

import com.example.atc.model.Gate;
import com.example.atc.model.Plane;
import com.example.atc.service.GateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

//http://localhost:8080/v1/gate
@RequestMapping("v1/gate")
@CrossOrigin(maxAge = 3600)
@RestController
public class GateController {

    private final GateService gateService;

    @Autowired
    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @PostMapping
    public void addPGate(@Valid @NonNull @RequestBody Gate gate) {
        gateService.addGate(gate);
    }

    @GetMapping
    public List<Gate> getAllGates() {
        return gateService.getAllGates();
    }


    @GetMapping(path = "{id}")
    public Gate getGateById(@PathVariable("id") int gate_id) {
        return gateService.getGateById(gate_id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deletePlaneById(@PathVariable("id") int gate_id) {
        gateService.deleteGate(gate_id);
    }


    @PutMapping(path = "{id}")
    public void updateGateById(@PathVariable("id") int gate_id, @Valid @NonNull @RequestBody Gate gateToUpdate) {
        gateService.updateGate(gate_id, gateToUpdate);
    }


}
