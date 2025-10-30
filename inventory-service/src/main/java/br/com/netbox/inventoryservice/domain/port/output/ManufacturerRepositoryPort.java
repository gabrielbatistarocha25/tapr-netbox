package br.com.netbox.inventoryservice.domain.port.output;

import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import java.util.List;
import java.util.Optional;

public interface ManufacturerRepositoryPort {
    Manufacturer save(Manufacturer manufacturer);
    List<Manufacturer> findAll();
    Optional<Manufacturer> findById(Long id);
}