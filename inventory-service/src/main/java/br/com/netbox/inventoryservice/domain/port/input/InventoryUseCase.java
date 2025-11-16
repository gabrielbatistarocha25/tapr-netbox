package br.com.netbox.inventoryservice.domain.port.input;

import br.com.netbox.inventoryservice.domain.model.Device;
import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import java.util.List;

public interface InventoryUseCase {
    Manufacturer createManufacturer(Manufacturer manufacturer);
    List<Manufacturer> getAllManufacturers();
    Manufacturer getManufacturerById(Long id); 
    Manufacturer updateManufacturer(Long id, Manufacturer manufacturer); 
    void deleteManufacturer(Long id); 

    DeviceModel createDeviceModel(DeviceModel deviceModel);
    List<DeviceModel> getAllDeviceModels();
    DeviceModel getDeviceModelById(Long id);
    DeviceModel updateDeviceModel(Long id, DeviceModel deviceModel);
    void deleteDeviceModel(Long id);

    Device createDevice(Device device);
    List<Device> getAllDevices();
    List<Device> getDevicesBySite(Long siteId);
    Device getDeviceById(Long id); 
    Device updateDevice(Long id, Device device); 
    void deleteDevice(Long id);
}