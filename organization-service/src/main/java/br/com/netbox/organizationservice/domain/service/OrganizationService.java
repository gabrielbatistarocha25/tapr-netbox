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
    public List<Location> getAllLocations() {
        return locationRepositoryPort.findAll();
    }

    @Override
    public Location getLocationById(Long id) {
         return locationRepositoryPort.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Localização com id " + id + " não encontrada."));
    }

    @Override
    public Location updateLocation(Long id, Location locationUpdate) {
        Location existingLocation = getLocationById(id);
        existingLocation.setName(locationUpdate.getName());
        existingLocation.setAddress(locationUpdate.getAddress());
        return locationRepositoryPort.save(existingLocation);
    }

    @Override
    public void deleteLocation(Long id) {
        if (!locationRepositoryPort.existsById(id)) {
             throw new EntityNotFoundException("Localização com id " + id + " não encontrada.");
        }
        locationRepositoryPort.deleteById(id);
    }


    @Override
    public Site createSite(Site site) {
        if (site.getLocation() == null || site.getLocation().getId() == null) {
            throw new IllegalArgumentException("O ID da Localização é obrigatório.");
        }
        Location location = getLocationById(site.getLocation().getId());
        site.setLocation(location);
        return siteRepositoryPort.save(site);
    }

    @Override
    public List<Site> getAllSites() {
        return siteRepositoryPort.findAllSites();
    }

    @Override
    public Site getSiteById(Long id) {
        return siteRepositoryPort.findSiteById(id)
            .orElseThrow(() -> new EntityNotFoundException("Site com id " + id + " não encontrado."));
    }

    @Override
    public Site updateSite(Long id, Site siteUpdate) {
        Site existingSite = getSiteById(id);
        
        if (siteUpdate.getLocation() == null || siteUpdate.getLocation().getId() == null) {
            throw new IllegalArgumentException("O ID da Localização é obrigatório para atualizar o Site.");
        }

        Location newLocation = getLocationById(siteUpdate.getLocation().getId());
        
        existingSite.setName(siteUpdate.getName());
        existingSite.setLocation(newLocation);
        
        return siteRepositoryPort.save(existingSite);
    }

    @Override
    public void deleteSite(Long id) {
        if (!siteRepositoryPort.siteExistsById(id)) {
             throw new EntityNotFoundException("Site com id " + id + " não encontrado.");
        }
        siteRepositoryPort.deleteSiteById(id);
    }
}