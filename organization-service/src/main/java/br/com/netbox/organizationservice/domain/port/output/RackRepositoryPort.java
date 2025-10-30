package br.com.netbox.organizationservice.domain.port.output;

import br.com.netbox.organizationservice.domain.model.Rack;
import java.util.List;
import java.util.Optional;

public interface RackRepositoryPort {
    Rack save(Rack rack);
    List<Rack> findAllRacks();
    Optional<Rack> findRackById(Long id);
    boolean rackExistsById(Long id);
}