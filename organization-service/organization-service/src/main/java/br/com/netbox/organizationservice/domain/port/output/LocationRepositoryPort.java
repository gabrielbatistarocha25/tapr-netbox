package br.com.netbox.organizationservice.domain.port.output;

import br.com.netbox.organizationservice.domain.model.Location;
import java.util.List;
import java.util.Optional;

public interface LocationRepositoryPort {
    Location save(Location location);
    List<Location> findAll();
    Optional<Location> findById(Long id);
    boolean existsById(Long id);
}