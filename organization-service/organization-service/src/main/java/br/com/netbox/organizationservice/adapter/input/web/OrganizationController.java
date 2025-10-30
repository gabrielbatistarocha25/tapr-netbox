package br.com.netbox.organizationservice.adapter.input.web;

import br.com.netbox.organizationservice.domain.model.Location;
import br.com.netbox.organizationservice.domain.model.Site;
import br.com.netbox.organizationservice.domain.port.input.OrganizationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization") 
public class OrganizationController {

    private final OrganizationUseCase organizationUseCase;

    public OrganizationController(OrganizationUseCase organizationUseCase) {
        this.organizationUseCase = organizationUseCase;
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location createdLocation = organizationUseCase.createLocation(location);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(organizationUseCase.getAllLocations());
    }

    @GetMapping("/locations/{id}") 
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(organizationUseCase.getLocationById(id));
    }

    @PostMapping("/sites")
    public ResponseEntity<Site> createSite(@RequestBody Site site) {
        Site createdSite = organizationUseCase.createSite(site);
        return new ResponseEntity<>(createdSite, HttpStatus.CREATED);
    }

    @GetMapping("/sites")
    public ResponseEntity<List<Site>> getAllSites() {
        return ResponseEntity.ok(organizationUseCase.getAllSites());
    }

    @GetMapping("/sites/{id}") 
    public ResponseEntity<Site> getSiteById(@PathVariable Long id) {
        organizationUseCase.getAllSites().stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Site com id " + id + " não encontrado."));
        return ResponseEntity.ok().build();
    }
}