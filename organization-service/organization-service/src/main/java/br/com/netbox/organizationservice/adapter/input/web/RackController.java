package br.com.netbox.organizationservice.adapter.input.web;

import br.com.netbox.organizationservice.domain.model.Rack;
import br.com.netbox.organizationservice.domain.port.input.RackUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racks")
public class RackController {

    private final RackUseCase rackUseCase;

    public RackController(RackUseCase rackUseCase) {
        this.rackUseCase = rackUseCase;
    }

    @PostMapping
    public ResponseEntity<Rack> createRack(@Valid @RequestBody Rack rack) {
        Rack createdRack = rackUseCase.createRack(rack);
        return new ResponseEntity<>(createdRack, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rack>> getAllRacks() {
        return ResponseEntity.ok(rackUseCase.getAllRacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rack> getRackById(@PathVariable Long id) {
        return ResponseEntity.ok(rackUseCase.getRackById(id));
    }
}