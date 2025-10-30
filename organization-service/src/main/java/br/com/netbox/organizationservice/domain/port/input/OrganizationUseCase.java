package br.com.netbox.organizationservice.domain.port.input;

import br.com.netbox.organizationservice.domain.model.Location;
import br.com.netbox.organizationservice.domain.model.Site;
import java.util.List;

public interface OrganizationUseCase {
    Location createLocation(Location location);
    Site createSite(Site site);
    List<Location> getAllLocations();
    List<Site> getAllSites();
    Location getLocationById(Long id);
}