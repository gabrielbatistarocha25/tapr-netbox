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
import jakarta.persistence.EntityNotFoundException; // Importe este
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
        LocationEntity entity;
        if (location.getId() != null) {
            entity = locationJpaRepository.findById(location.getId())
                .orElseThrow(() -> new EntityNotFoundException("Localização com id " + location.getId() + " não encontrada."));
        } else {
            entity = new LocationEntity();
        }
        
        entity.setName(location.getName());
        entity.setAddress(location.getAddress());

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
    public void deleteById(Long id) {
        locationJpaRepository.deleteById(id);
    }

    @Override
    public Site save(Site site) {
        SiteEntity entity;
        if (site.getId() != null) {
            entity = siteJpaRepository.findById(site.getId())
                .orElseThrow(() -> new EntityNotFoundException("Site com id " + site.getId() + " não encontrado."));
        } else {
            entity = new SiteEntity();
        }

        entity.setName(site.getName());
        
        if (site.getLocation() != null && site.getLocation().getId() != null) {
            LocationEntity location = locationJpaRepository.findById(site.getLocation().getId())
                 .orElseThrow(() -> new EntityNotFoundException("Localização com id " + site.getLocation().getId() + " não encontrada."));
            entity.setLocation(location);
        } else {
            throw new IllegalArgumentException("ID da Localização é obrigatório para salvar o Site.");
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
    public void deleteSiteById(Long id) {
        siteJpaRepository.deleteById(id);
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

    @Override
    public void deleteRackById(Long id) {
        rackJpaRepository.deleteById(id);
    }
}