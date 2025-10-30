package br.com.netbox.organizationservice.adapter.output.persistence;

import br.com.netbox.organizationservice.adapter.output.persistence.entity.LocationEntity;
import br.com.netbox.organizationservice.adapter.output.persistence.entity.RackEntity;
import br.com.netbox.organizationservice.adapter.output.persistence.entity.SiteEntity;
import br.com.netbox.organizationservice.adapter.output.persistence.mapper.LocationMapper;
import br.com.netbox.organizationservice.adapter.output.persistence.mapper.RackMapper;
import br.com.netbox.organizationservice.adapter.output.persistence.mapper.SiteMapper;
import br.com.netbox.organizationservice.adapter.output.persistence.repository.LocationJpaRepository;
import br.com.netbox.organizationservice.adapter.output.persistence.repository.RackJpaRepository;
import br.com.netbox.organizationservice.adapter.output.persistence.repository.SiteJpaRepository;
import br.com.netbox.organizationservice.domain.model.Location;
import br.com.netbox.organizationservice.domain.model.Rack;
import br.com.netbox.organizationservice.domain.model.Site;
import br.com.netbox.organizationservice.domain.port.output.LocationRepositoryPort;
import br.com.netbox.organizationservice.domain.port.output.RackRepositoryPort;
import br.com.netbox.organizationservice.domain.port.output.SiteRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrganizationPersistenceAdapter implements LocationRepositoryPort, SiteRepositoryPort, RackRepositoryPort {

    private final LocationJpaRepository locationJpaRepository;
    private final SiteJpaRepository siteJpaRepository;
    private final RackJpaRepository rackJpaRepository;
    private final LocationMapper locationMapper;
    private final SiteMapper siteMapper;
    private final RackMapper rackMapper;

    public OrganizationPersistenceAdapter(LocationJpaRepository locationJpaRepository, SiteJpaRepository siteJpaRepository, RackJpaRepository rackJpaRepository, LocationMapper locationMapper, SiteMapper siteMapper, RackMapper rackMapper) {
        this.locationJpaRepository = locationJpaRepository;
        this.siteJpaRepository = siteJpaRepository;
        this.rackJpaRepository = rackJpaRepository;
        this.locationMapper = locationMapper;
        this.siteMapper = siteMapper;
        this.rackMapper = rackMapper;
    }

    @Override
    public Location save(Location location) {
        LocationEntity entity = locationMapper.toEntity(location);
        return locationMapper.toModel(locationJpaRepository.save(entity));
    }

    @Override
    public List<Location> findAll() { 
        return locationJpaRepository.findAll().stream()
                .map(locationMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Location> findById(Long id) { 
        return locationJpaRepository.findById(id).map(locationMapper::toModel);
    }

    @Override
    public boolean existsById(Long id) { 
        return locationJpaRepository.existsById(id);
    }

    @Override
    public Site save(Site site) {
        SiteEntity entity = siteMapper.toEntity(site);
        if (entity.getLocation() != null && entity.getLocation().getId() != null) {
            locationJpaRepository.findById(entity.getLocation().getId())
                .ifPresent(entity::setLocation);
        }
        return siteMapper.toModel(siteJpaRepository.save(entity));
    }

    @Override
    public List<Site> findAllSites() { 
        return siteJpaRepository.findAll().stream()
                .map(siteMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Site> findSiteById(Long id) { 
        return siteJpaRepository.findById(id).map(siteMapper::toModel);
    }

    @Override
    public boolean siteExistsById(Long id) { 
         return siteJpaRepository.existsById(id);
    }

    @Override
    public Rack save(Rack rack) {
        RackEntity entity = rackMapper.toEntity(rack);
        if (entity.getSite() != null && entity.getSite().getId() != null) {
            siteJpaRepository.findById(entity.getSite().getId())
                .ifPresent(entity::setSite);
        }
        return rackMapper.toModel(rackJpaRepository.save(entity));
    }

    @Override
    public List<Rack> findAllRacks() { 
        return rackJpaRepository.findAll().stream()
                .map(rackMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rack> findRackById(Long id) { 
        return rackJpaRepository.findById(id).map(rackMapper::toModel);
    }

    @Override
     public boolean rackExistsById(Long id) { 
         return rackJpaRepository.existsById(id);
    }
}