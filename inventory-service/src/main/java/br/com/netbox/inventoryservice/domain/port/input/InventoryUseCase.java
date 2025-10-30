package br.com.netbox.inventoryservice.domain.port.input;

import br.com.netbox.inventoryservice.domain.model.Device;
import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import java.util.List;

public interface InventoryUseCase {
    Manufacturer createManufacturer(Manufacturer manufacturer);
    List<Manufacturer> getAllManufacturers();

    DeviceModel createDeviceModel(DeviceModel deviceModel);
    List<DeviceModel> getAllDeviceModels();

    Device createDevice(Device device);
    List<Device> getAllDevices();
    List<Device> getDevicesBySite(Long siteId);
}