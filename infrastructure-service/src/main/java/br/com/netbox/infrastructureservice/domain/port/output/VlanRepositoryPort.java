package br.com.netbox.infrastructureservice.domain.port.output;

import br.com.netbox.infrastructureservice.domain.model.Vlan;
import java.util.List;
import java.util.Optional;

public interface VlanRepositoryPort {
    Vlan save(Vlan vlan);
    List<Vlan> findAll();
    Optional<Vlan> findById(Long id);
    List<Vlan> findBySiteId(Long siteId);
}