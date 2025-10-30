package br.com.netbox.organizationservice.domain.port.output;

import br.com.netbox.organizationservice.domain.model.Site;
import java.util.List;
import java.util.Optional;

public interface SiteRepositoryPort {
    Site save(Site site);
    List<Site> findAllSites();
    Optional<Site> findSiteById(Long id);
    boolean siteExistsById(Long id);
}