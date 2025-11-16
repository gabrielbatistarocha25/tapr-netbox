package br.com.netbox.infrastructureservice.domain.port.output;

import br.com.netbox.infrastructureservice.domain.model.Prefix;
import java.util.List;
import java.util.Optional;

public interface PrefixRepositoryPort {
    Prefix save(Prefix prefix);
    Optional<Prefix> findById(Long id);
    List<Prefix> findAll();
    List<Prefix> findBySiteId(Long siteId);
    void deletePrefixById(Long id);
    boolean prefixExistsById(Long id);
}