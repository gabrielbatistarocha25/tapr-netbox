package br.com.netbox.organizationservice.domain.port.input;

import br.com.netbox.organizationservice.domain.model.Location;
import br.com.netbox.organizationservice.domain.model.Site;
import java.util.List;

public interface OrganizationUseCase {
    Location createLocation(Location location);
    List<Location> getAllLocations();
    Location getLocationById(Long id);
    Location updateLocation(Long id, Location location);
    void deleteLocation(Long id); 

    Site createSite(Site site);
    List<Site> getAllSites();
    Site getSiteById(Long id); 
    Site updateSite(Long id, Site site);
    void deleteSite(Long id);
}