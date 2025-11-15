package br.com.netbox.inventoryservice.domain.port.input;

import br.com.netbox.inventoryservice.domain.model.Device;
import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import java.util.List;

public interface InventoryUseCase {
    // Manufacturer
    Manufacturer createManufacturer(Manufacturer manufacturer);
    List<Manufacturer> getAllManufacturers();
    Manufacturer getManufacturerById(Long id); // <-- ADICIONAR
    Manufacturer updateManufacturer(Long id, Manufacturer manufacturer); // <-- ADICIONAR
    void deleteManufacturer(Long id); // <-- ADICIONAR

    // DeviceModel
    DeviceModel createDeviceModel(DeviceModel deviceModel);
    List<DeviceModel> getAllDeviceModels();
    DeviceModel getDeviceModelById(Long id); // <-- ADICIONAR
    DeviceModel updateDeviceModel(Long id, DeviceModel deviceModel); // <-- ADICIONAR
    void deleteDeviceModel(Long id); // <-- ADICIONAR

    // Device
    Device createDevice(Device device);
    List<Device> getAllDevices();
    List<Device> getDevicesBySite(Long siteId);
    Device getDeviceById(Long id); // <-- ADICIONAR
    Device updateDevice(Long id, Device device); // <-- ADICIONAR
    void deleteDevice(Long id); // <-- ADICIONAR
}