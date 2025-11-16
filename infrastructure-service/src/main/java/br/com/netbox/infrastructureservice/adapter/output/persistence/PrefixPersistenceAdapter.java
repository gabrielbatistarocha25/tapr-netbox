package br.com.netbox.infrastructureservice.adapter.output.persistence;

import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.PrefixEntity;
import br.com.netbox.infrastructureservice.adapter.output.persistence.mapper.PrefixMapper;
import br.com.netbox.infrastructureservice.adapter.output.persistence.repository.PrefixJpaRepository;
import br.com.netbox.infrastructureservice.domain.model.Prefix;
import br.com.netbox.infrastructureservice.domain.port.output.PrefixRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PrefixPersistenceAdapter implements PrefixRepositoryPort {

    private final PrefixJpaRepository prefixJpaRepository;
    private final PrefixMapper prefixMapper;

    public PrefixPersistenceAdapter(PrefixJpaRepository prefixJpaRepository, PrefixMapper prefixMapper) {
        this.prefixJpaRepository = prefixJpaRepository;
        this.prefixMapper = prefixMapper;
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
}