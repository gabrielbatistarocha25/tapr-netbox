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

    // --- Location Endpoints ---

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

    @PutMapping("/locations/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        Location updatedLocation = organizationUseCase.updateLocation(id, location);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/locations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable Long id) {
        organizationUseCase.deleteLocation(id);
    }

    // --- Site Endpoints ---

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
        // (Este método foi corrigido na sua última interação)
        Site site = organizationUseCase.getSiteById(id);
        return ResponseEntity.ok(site);
    }

    @PutMapping("/sites/{id}")
    public ResponseEntity<Site> updateSite(@PathVariable Long id, @RequestBody Site site) {
        Site updatedSite = organizationUseCase.updateSite(id, site);
        return ResponseEntity.ok(updatedSite);
    }

    @DeleteMapping("/sites/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSite(@PathVariable Long id) {
        organizationUseCase.deleteSite(id);
    }
}