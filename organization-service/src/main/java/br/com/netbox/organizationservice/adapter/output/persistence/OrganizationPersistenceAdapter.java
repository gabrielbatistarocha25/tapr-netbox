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

    // --- Location ---
    @Override
    public Location save(Location location) {
        LocationEntity entity;
        if (location.getId() != null) {
            // É um UPDATE: Carregue a entidade gerenciada (Corrigido na etapa anterior)
            entity = locationJpaRepository.findById(location.getId())
                .orElseThrow(() -> new EntityNotFoundException("Localização com id " + location.getId() + " não encontrada."));
        } else {
            // É um CREATE: Crie uma nova entidade
            entity = new LocationEntity();
        }
        
        entity.setName(location.getName());
        entity.setAddress(location.getAddress());
        // NÃO mexemos na lista de sites

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

    // --- Site ---
    
    // --- MÉTODO CORRIGIDO ---
    @Override
    public Site save(Site site) {
        SiteEntity entity;
        if (site.getId() != null) {
            // É um UPDATE: Carregue a entidade gerenciada
            entity = siteJpaRepository.findById(site.getId())
                .orElseThrow(() -> new EntityNotFoundException("Site com id " + site.getId() + " não encontrado."));
        } else {
            // É um CREATE: Crie uma nova entidade
            entity = new SiteEntity();
        }

        // Mapeie os campos do modelo para a entidade
        entity.setName(site.getName());
        
        // Valide e atualize a Location
        if (site.getLocation() != null && site.getLocation().getId() != null) {
            LocationEntity location = locationJpaRepository.findById(site.getLocation().getId())
                 .orElseThrow(() -> new EntityNotFoundException("Localização com id " + site.getLocation().getId() + " não encontrada."));
            entity.setLocation(location);
        } else {
            throw new IllegalArgumentException("ID da Localização é obrigatório para salvar o Site.");
        }
        // Nós NÃO mexemos na lista de racks aqui, preservando as associações

        return siteMapper.toModel(siteJpaRepository.save(entity));
    }
    // --- FIM DA CORREÇÃO ---


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

    // --- Rack ---
    @Override
    public Rack save(Rack rack) {
        // Este método está correto, pois Rack não tem filhos com orphanRemoval.
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