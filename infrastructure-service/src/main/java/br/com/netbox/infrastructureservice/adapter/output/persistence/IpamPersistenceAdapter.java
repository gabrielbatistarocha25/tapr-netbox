package br.com.netbox.infrastructureservice.adapter.output.persistence;

import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.IpAddressEntity;
import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.PrefixEntity;
import br.com.netbox.infrastructureservice.adapter.output.persistence.mapper.IpAddressMapper;
import br.com.netbox.infrastructureservice.adapter.output.persistence.mapper.PrefixMapper;
import br.com.netbox.infrastructureservice.adapter.output.persistence.repository.IpAddressJpaRepository;
import br.com.netbox.infrastructureservice.adapter.output.persistence.repository.PrefixJpaRepository;
import br.com.netbox.infrastructureservice.domain.model.IpAddress;
import br.com.netbox.infrastructureservice.domain.model.Prefix;
import br.com.netbox.infrastructureservice.domain.port.output.IpAddressRepositoryPort;
import br.com.netbox.infrastructureservice.domain.port.output.PrefixRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IpamPersistenceAdapter implements PrefixRepositoryPort, IpAddressRepositoryPort {

    private final PrefixJpaRepository prefixJpaRepository;
    private final IpAddressJpaRepository ipAddressJpaRepository;
    private final PrefixMapper prefixMapper;
    private final IpAddressMapper ipAddressMapper;

    public IpamPersistenceAdapter(PrefixJpaRepository prefixJpaRepository, IpAddressJpaRepository ipAddressJpaRepository, PrefixMapper prefixMapper, IpAddressMapper ipAddressMapper) {
        this.prefixJpaRepository = prefixJpaRepository;
        this.ipAddressJpaRepository = ipAddressJpaRepository;
        this.prefixMapper = prefixMapper;
        this.ipAddressMapper = ipAddressMapper;
    }

    @Override
    public Prefix save(Prefix prefix) {
        PrefixEntity entity;
        if (prefix.getId() != null) {
            entity = prefixJpaRepository.findById(prefix.getId())
                .orElseThrow(() -> new EntityNotFoundException("Prefix com id " + prefix.getId() + " não encontrado."));
        } else {
            entity = new PrefixEntity();
        }
        
        entity.setNetworkAddress(prefix.getNetworkAddress());
        entity.setGateway(prefix.getGateway());
        entity.setDescription(prefix.getDescription());
        entity.setSiteId(prefix.getSiteId());
        entity.setVlanId(prefix.getVlanId());
        
        return prefixMapper.toModel(prefixJpaRepository.save(entity));
    }

    @Override
    public Optional<Prefix> findById(Long id) {
        return prefixJpaRepository.findById(id).map(prefixMapper::toModel);
    }

    @Override
    public List<Prefix> findAll() {
        return prefixJpaRepository.findAll().stream().map(prefixMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Prefix> findBySiteId(Long siteId) {
        return prefixJpaRepository.findBySiteId(siteId).stream().map(prefixMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public void deletePrefixById(Long id) {
        prefixJpaRepository.deleteById(id);
    }

    @Override
    public boolean prefixExistsById(Long id) {
        return prefixJpaRepository.existsById(id);
    }

    @Override
    public IpAddress save(IpAddress ipAddress) {
        IpAddressEntity entity;
        if (ipAddress.getId() != null) {
            entity = ipAddressJpaRepository.findById(ipAddress.getId())
                .orElseThrow(() -> new EntityNotFoundException("IPAddress com id " + ipAddress.getId() + " não encontrado."));
        } else {
            entity = new IpAddressEntity();
        }
        
        entity.setAddress(ipAddress.getAddress());
        entity.setDescription(ipAddress.getDescription());
        entity.setAssignedDeviceId(ipAddress.getAssignedDeviceId());

        PrefixEntity prefix = prefixJpaRepository.findById(ipAddress.getPrefixId())
            .orElseThrow(() -> new EntityNotFoundException("Prefix com id " + ipAddress.getPrefixId() + " não encontrado."));
        entity.setPrefix(prefix);
        
        return ipAddressMapper.toModel(ipAddressJpaRepository.save(entity));
    }

    @Override
    public Optional<IpAddress> findById(Long id) {
        return ipAddressJpaRepository.findById(id).map(ipAddressMapper::toModel);
    }

    @Override
    public List<IpAddress> findAll() {
        return ipAddressJpaRepository.findAll().stream().map(ipAddressMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public List<IpAddress> findByPrefixId(Long prefixId) {
        return ipAddressJpaRepository.findByPrefixId(prefixId).stream().map(ipAddressMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteIpAddressById(Long id) {
        ipAddressJpaRepository.deleteById(id);
    }

    @Override
    public boolean ipAddressExistsById(Long id) {
        return ipAddressJpaRepository.existsById(id);
    }
}