package br.com.netbox.inventoryservice.domain.service;

import br.com.netbox.inventoryservice.domain.model.Device;
import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import br.com.netbox.inventoryservice.domain.port.input.InventoryUseCase;
import br.com.netbox.inventoryservice.domain.port.output.DeviceModelRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.DeviceRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.ManufacturerRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.OrganizationApiPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService implements InventoryUseCase {

    private final DeviceRepositoryPort deviceRepository;
    private final ManufacturerRepositoryPort manufacturerRepository;
    private final DeviceModelRepositoryPort deviceModelRepository;
    private final OrganizationApiPort organizationApiPort;

    public InventoryService(DeviceRepositoryPort deviceRepository, ManufacturerRepositoryPort manufacturerRepository, DeviceModelRepositoryPort deviceModelRepository, OrganizationApiPort organizationApiPort) {
        this.deviceRepository = deviceRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.deviceModelRepository = deviceModelRepository;
        this.organizationApiPort = organizationApiPort;
    }

    @Override
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer getManufacturerById(Long id) {
        return manufacturerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Fabricante com id " + id + " não encontrado."));
    }

    @Override
    public Manufacturer updateManufacturer(Long id, Manufacturer manufacturerUpdate) {
        Manufacturer existing = getManufacturerById(id);
        existing.setName(manufacturerUpdate.getName());
        return manufacturerRepository.save(existing);
    }

    @Override
    public void deleteManufacturer(Long id) {
        getManufacturerById(id);
        manufacturerRepository.deleteManufacturerById(id); 
    }

    @Override
    public DeviceModel createDeviceModel(DeviceModel deviceModel) {
        getManufacturerById(deviceModel.getManufacturer().getId());
        return deviceModelRepository.save(deviceModel);
    }

    @Override
    public List<DeviceModel> getAllDeviceModels() {
        return deviceModelRepository.findAllDeviceModels();
    }

    @Override
    public DeviceModel getDeviceModelById(Long id) {
         return deviceModelRepository.findDeviceModelById(id)
            .orElseThrow(() -> new EntityNotFoundException("Modelo de Dispositivo com id " + id + " não encontrado."));
    }

    @Override
    public DeviceModel updateDeviceModel(Long id, DeviceModel deviceModelUpdate) {
        DeviceModel existing = getDeviceModelById(id);
        
        Manufacturer manufacturer = getManufacturerById(deviceModelUpdate.getManufacturer().getId());

        existing.setName(deviceModelUpdate.getName());
        existing.setManufacturer(manufacturer);
        
        return deviceModelRepository.save(existing);
    }

    @Override
    public void deleteDeviceModel(Long id) {
        getDeviceModelById(id); 
        deviceModelRepository.deleteDeviceModelById(id); 
    }

    @Override
    public Device createDevice(Device device) {
        validateDeviceDependencies(device.getSiteId(), device.getRackId(), device.getDeviceModelId());
        return deviceRepository.save(device);
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAllDevices();
    }

    @Override
    public List<Device> getDevicesBySite(Long siteId) {
        if (!organizationApiPort.siteExists(siteId)) {
            throw new EntityNotFoundException("Site com id " + siteId + " não encontrado.");
        }
        return deviceRepository.findBySiteId(siteId);
    }

    @Override
    public Device getDeviceById(Long id) {
        return deviceRepository.findDeviceById(id)
            .orElseThrow(() -> new EntityNotFoundException("Dispositivo com id " + id + " não encontrado."));
    }

    @Override
    public Device updateDevice(Long id, Device deviceUpdate) {
        Device existing = getDeviceById(id);

        validateDeviceDependencies(deviceUpdate.getSiteId(), deviceUpdate.getRackId(), deviceUpdate.getDeviceModelId());

        existing.setName(deviceUpdate.getName());
        existing.setPosition(deviceUpdate.getPosition());
        existing.setSiteId(deviceUpdate.getSiteId());
        existing.setRackId(deviceUpdate.getRackId());
        existing.setDeviceModelId(deviceUpdate.getDeviceModelId());

        return deviceRepository.save(existing);
    }

    @Override
    public void deleteDevice(Long id) {
        getDeviceById(id); 
        deviceRepository.deleteDeviceById(id); 
    }

    private void validateDeviceDependencies(Long siteId, Long rackId, Long deviceModelId) {
        if (siteId == null) {
             throw new IllegalArgumentException("O ID do Site é obrigatório.");
        }
        if (!organizationApiPort.siteExists(siteId)) {
            throw new EntityNotFoundException("Site com id " + siteId + " não encontrado.");
        }

        if (rackId != null) {
            if (!organizationApiPort.rackExists(rackId)) {
                throw new EntityNotFoundException("Rack com id " + rackId + " não encontrado.");
            }
        }

        if (deviceModelId == null) {
            throw new IllegalArgumentException("O ID do Modelo de Dispositivo é obrigatório.");
        }
        getDeviceModelById(deviceModelId);
    }
}