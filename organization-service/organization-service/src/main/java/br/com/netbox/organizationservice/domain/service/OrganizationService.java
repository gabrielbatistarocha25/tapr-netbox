package br.com.netbox.organizationservice.domain.service;

import br.com.netbox.organizationservice.domain.model.Location;
import br.com.netbox.organizationservice.domain.model.Site;
import br.com.netbox.organizationservice.domain.port.input.OrganizationUseCase;
import br.com.netbox.organizationservice.domain.port.output.LocationRepositoryPort;
import br.com.netbox.organizationservice.domain.port.output.SiteRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 
public class OrganizationService implements OrganizationUseCase {

    private final LocationRepositoryPort locationRepositoryPort;
    private final SiteRepositoryPort siteRepositoryPort;

    public OrganizationService(LocationRepositoryPort locationRepositoryPort, SiteRepositoryPort siteRepositoryPort) {
        this.locationRepositoryPort = locationRepositoryPort;
        this.siteRepositoryPort = siteRepositoryPort;
    }

    @Override
    public Location createLocation(Location location) {
        return locationRepositoryPort.save(location);
    }

    @Override
    public Site createSite(Site site) {
        if (site.getLocation() == null || site.getLocation().getId() == null) {
            throw new IllegalArgumentException("O ID da Localização é obrigatório.");
        }

        Location location = locationRepositoryPort.findById(site.getLocation().getId())
                .orElseThrow(() -> new EntityNotFoundException("Localização com id " + site.getLocation().getId() + " não encontrada."));

        site.setLocation(location);
        return siteRepositoryPort.save(site);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepositoryPort.findAll();
    }

    @Override
    public List<Site> getAllSites() {
        return siteRepositoryPort.findAllSites();
    }

    @Override
    public Location getLocationById(Long id) {
         return locationRepositoryPort.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Localização com id " + id + " não encontrada."));
    }
}