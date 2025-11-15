package br.com.netbox.infrastructureservice.adapter.input.web;

import br.com.netbox.infrastructureservice.domain.model.Vlan;
import br.com.netbox.infrastructureservice.domain.port.input.NetworkUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vlans")
public class NetworkController {

    private final NetworkUseCase networkUseCase;

    public NetworkController(NetworkUseCase networkUseCase) {
        this.networkUseCase = networkUseCase;
    }

    @PostMapping
    public ResponseEntity<Vlan> createVlan(@Valid @RequestBody Vlan vlan) {
        Vlan createdVlan = networkUseCase.createVlan(vlan);
        return new ResponseEntity<>(createdVlan, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vlan>> getAllVlans() {
        List<Vlan> vlans = networkUseCase.getAllVlans();
        return ResponseEntity.ok(vlans);
    }

    @GetMapping("/site/{siteId}")
    public ResponseEntity<List<Vlan>> getVlansBySite(@PathVariable Long siteId) {
        List<Vlan> vlans = networkUseCase.getVlansBySite(siteId);
        return ResponseEntity.ok(vlans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vlan> getVlanById(@PathVariable Long id) {
        Vlan vlan = networkUseCase.getVlanById(id);
        return ResponseEntity.ok(vlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vlan> updateVlan(@PathVariable Long id, @Valid @RequestBody Vlan vlan) {
        Vlan updatedVlan = networkUseCase.updateVlan(id, vlan);
        return ResponseEntity.ok(updatedVlan);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVlan(@PathVariable Long id) {
        networkUseCase.deleteVlan(id);
    }
}