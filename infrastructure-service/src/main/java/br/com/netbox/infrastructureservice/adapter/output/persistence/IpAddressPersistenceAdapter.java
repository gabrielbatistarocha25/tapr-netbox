package br.com.netbox.infrastructureservice.adapter.output.persistence;

import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.IpAddressEntity;
import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.PrefixEntity;
import br.com.netbox.infrastructureservice.adapter.output.persistence.mapper.IpAddressMapper;
import br.com.netbox.infrastructureservice.adapter.output.persistence.repository.IpAddressJpaRepository;
import br.com.netbox.infrastructureservice.adapter.output.persistence.repository.PrefixJpaRepository;
import br.com.netbox.infrastructureservice.domain.model.IpAddress;
import br.com.netbox.infrastructureservice.domain.port.output.IpAddressRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IpAddressPersistenceAdapter implements IpAddressRepositoryPort {

    private final PrefixJpaRepository prefixJpaRepository;
    private final IpAddressJpaRepository ipAddressJpaRepository;
    private final IpAddressMapper ipAddressMapper;

    public IpAddressPersistenceAdapter(PrefixJpaRepository prefixJpaRepository, IpAddressJpaRepository ipAddressJpaRepository, IpAddressMapper ipAddressMapper) {
        this.prefixJpaRepository = prefixJpaRepository;
        this.ipAddressJpaRepository = ipAddressJpaRepository;
        this.ipAddressMapper = ipAddressMapper;
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