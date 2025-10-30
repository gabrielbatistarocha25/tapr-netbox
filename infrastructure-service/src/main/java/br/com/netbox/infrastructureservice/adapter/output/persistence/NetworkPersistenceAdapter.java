package br.com.netbox.infrastructureservice.adapter.output.persistence;

import br.com.netbox.infrastructureservice.adapter.output.persistence.mapper.VlanMapper;
import br.com.netbox.infrastructureservice.adapter.output.persistence.repository.VlanJpaRepository;
import br.com.netbox.infrastructureservice.domain.model.Vlan;
import br.com.netbox.infrastructureservice.domain.port.output.VlanRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NetworkPersistenceAdapter implements VlanRepositoryPort {

    private final VlanJpaRepository vlanJpaRepository;
    private final VlanMapper vlanMapper;

    public NetworkPersistenceAdapter(VlanJpaRepository vlanJpaRepository, VlanMapper vlanMapper) {
        this.vlanJpaRepository = vlanJpaRepository;
        this.vlanMapper = vlanMapper;
    }

    @Override
    public Vlan save(Vlan vlan) {
        var entity = vlanMapper.toEntity(vlan);
        return vlanMapper.toModel(vlanJpaRepository.save(entity));
    }

    @Override
    public List<Vlan> findAll() {
        return vlanJpaRepository.findAll().stream()
                .map(vlanMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Vlan> findById(Long id) {
        return vlanJpaRepository.findById(id).map(vlanMapper::toModel);
    }

    @Override
    public List<Vlan> findBySiteId(Long siteId) {
        return vlanJpaRepository.findBySiteId(siteId).stream()
                .map(vlanMapper::toModel)
                .collect(Collectors.toList());
    }
}