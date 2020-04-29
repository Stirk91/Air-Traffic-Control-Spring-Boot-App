

package com.example.atc.api;

import com.example.atc.model.Plane;
import com.example.atc.model.Runway;
import com.example.atc.service.PlaneService;
import com.example.atc.service.RunwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

//http://localhost:8080/v1/plane
@RequestMapping("v1/runway")
@RestController
public class RunwayController {

    private final RunwayService runwayService;

    @Autowired
    public RunwayController(RunwayService runwayService) {
        this.runwayService = runwayService;
    }

    @PostMapping
    public void addRunway(@Valid @NonNull @RequestBody Runway runway) {
        runwayService.addRunway(runway);
    }

    @GetMapping
    public List<Runway> getAllRunways() {
        return runwayService.getAllRunways();
    }

    @GetMapping(path = "{id}")
    public Runway getRunwayById(@PathVariable("id") int runway_id) {
        return runwayService.getRunwayById(runway_id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteRunwayById(@PathVariable("id") int runway_id) {
        runwayService.deleteRunway(runway_id);
    }

    @PutMapping(path = "{id}")
    public void updateRunwayById(@PathVariable("id") int runway_id, @Valid @NonNull @RequestBody Runway runwayToUpdate) {
        runwayService.updateRunway(runway_id, runwayToUpdate);
    }


}