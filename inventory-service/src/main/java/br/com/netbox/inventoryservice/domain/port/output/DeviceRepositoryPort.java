package br.com.netbox.inventoryservice.domain.port.output;

import br.com.netbox.inventoryservice.domain.model.Device;
import java.util.List;
import java.util.Optional;

public interface DeviceRepositoryPort {
    Device save(Device device);
    List<Device> findAllDevices();
    Optional<Device> findDeviceById(Long id);
    List<Device> findBySiteId(Long siteId);
}