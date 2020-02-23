package com.example.atc.api;

import com.example.atc.model.Plane;
import com.example.atc.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("v1/plane")
@RestController
public class PlaneController {

    private final PlaneService planeService;

    @Autowired
    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    @PostMapping
    public void addPlane(@Valid @NonNull @RequestBody Plane plane) {
        planeService.addPlane(plane);
        }

    @GetMapping
    public List<Plane> getAllPlanes() {
        return planeService.getAllPlanes();
    }

    @GetMapping(path = "{id}")
    public Plane getPlaneById(@PathVariable("id") UUID id) {
        return planeService.getPlaneById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deletePlaneById(@PathVariable("id") UUID id) {
        planeService.deletePlane(id);
    }

    @PutMapping(path = "{id}")
    public void updatePlaneById(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Plane planeToUpdate) {
        planeService.updatePlane(id, planeToUpdate);
    }


}
