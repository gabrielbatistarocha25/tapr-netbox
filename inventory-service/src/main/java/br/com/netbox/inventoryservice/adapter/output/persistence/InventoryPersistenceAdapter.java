package br.com.netbox.inventoryservice.adapter.output.persistence;

import br.com.netbox.inventoryservice.adapter.output.persistence.entity.DeviceEntity;
import br.com.netbox.inventoryservice.adapter.output.persistence.entity.DeviceModelEntity;
import br.com.netbox.inventoryservice.adapter.output.persistence.entity.ManufacturerEntity;
import br.com.netbox.inventoryservice.adapter.output.persistence.mapper.DeviceMapper;
import br.com.netbox.inventoryservice.adapter.output.persistence.mapper.DeviceModelMapper;
import br.com.netbox.inventoryservice.adapter.output.persistence.mapper.ManufacturerMapper;
import br.com.netbox.inventoryservice.adapter.output.persistence.repository.DeviceJpaRepository;
import br.com.netbox.inventoryservice.adapter.output.persistence.repository.DeviceModelJpaRepository;
import br.com.netbox.inventoryservice.adapter.output.persistence.repository.ManufacturerJpaRepository;
import br.com.netbox.inventoryservice.domain.model.Device;
import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import br.com.netbox.inventoryservice.domain.port.output.DeviceModelRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.DeviceRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.ManufacturerRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InventoryPersistenceAdapter implements ManufacturerRepositoryPort, DeviceModelRepositoryPort, DeviceRepositoryPort {

    // Repositórios
    private final ManufacturerJpaRepository manufacturerJpaRepository;
    private final DeviceModelJpaRepository deviceModelJpaRepository;
    private final DeviceJpaRepository deviceJpaRepository;

    // Mappers
    private final ManufacturerMapper manufacturerMapper;
    private final DeviceModelMapper deviceModelMapper;
    private final DeviceMapper deviceMapper;

    // Construtor
    public InventoryPersistenceAdapter(ManufacturerJpaRepository manufacturerJpaRepository, DeviceModelJpaRepository deviceModelJpaRepository, DeviceJpaRepository deviceJpaRepository, ManufacturerMapper manufacturerMapper, DeviceModelMapper deviceModelMapper, DeviceMapper deviceMapper) {
        this.manufacturerJpaRepository = manufacturerJpaRepository;
        this.deviceModelJpaRepository = deviceModelJpaRepository;
        this.deviceJpaRepository = deviceJpaRepository;
        this.manufacturerMapper = manufacturerMapper;
        this.deviceModelMapper = deviceModelMapper;
        this.deviceMapper = deviceMapper;
    }

    // --- ManufacturerRepositoryPort Methods ---

    // --- MÉTODO CORRIGIDO ---
    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        ManufacturerEntity entity;
        if (manufacturer.getId() != null) {
            // É um UPDATE: Carregue a entidade gerenciada
            entity = manufacturerJpaRepository.findById(manufacturer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Fabricante com id " + manufacturer.getId() + " não encontrado."));
        } else {
            // É um CREATE: Crie uma nova entidade
            entity = new ManufacturerEntity();
        }
        
        // Mapeie os campos do modelo para a entidade
        entity.setName(manufacturer.getName());
        // Nós NÃO mexemos na lista de deviceModels aqui, preservando as associações

        return manufacturerMapper.toModel(manufacturerJpaRepository.save(entity));
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerJpaRepository.findAll().stream()
                .map(manufacturerMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return manufacturerJpaRepository.findById(id).map(manufacturerMapper::toModel);
    }

    @Override
    public void deleteManufacturerById(Long id) {
        manufacturerJpaRepository.deleteById(id);
    }

    // --- DeviceModelRepositoryPort Methods ---

    // --- MÉTODO CORRIGIDO ---
    @Override
    @Transactional
    public DeviceModel save(DeviceModel deviceModel) {
        DeviceModelEntity entity;
        if (deviceModel.getId() != null) {
            // É um UPDATE: Carregue a entidade gerenciada
            entity = deviceModelJpaRepository.findById(deviceModel.getId())
                .orElseThrow(() -> new EntityNotFoundException("Modelo de Dispositivo com id " + deviceModel.getId() + " não encontrado."));
        } else {
            // É um CREATE: Crie uma nova entidade
            entity = new DeviceModelEntity();
        }

        // Mapeie os campos do modelo para a entidade
        entity.setName(deviceModel.getName());
        
        // Valide e atualize o Fabricante
        if (deviceModel.getManufacturer() != null && deviceModel.getManufacturer().getId() != null) {
            ManufacturerEntity manufacturerEntity = manufacturerJpaRepository.findById(deviceModel.getManufacturer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Fabricante com id " + deviceModel.getManufacturer().getId() + " não encontrado ao salvar DeviceModel."));
            entity.setManufacturer(manufacturerEntity);
        } else {
             throw new IllegalArgumentException("ID do Fabricante é obrigatório para salvar DeviceModel.");
        }
        // Nós NÃO mexemos na lista de devices aqui, preservando as associações

        return deviceModelMapper.toModel(deviceModelJpaRepository.save(entity));
    }

    @Override
    public List<DeviceModel> findAllDeviceModels() {
        return deviceModelJpaRepository.findAll().stream()
                .map(deviceModelMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DeviceModel> findDeviceModelById(Long id) {
        return deviceModelJpaRepository.findById(id).map(deviceModelMapper::toModel);
    }

    @Override
    public void deleteDeviceModelById(Long id) {
        deviceModelJpaRepository.deleteById(id);
    }

    // --- DeviceRepositoryPort Methods ---
    @Override
    @Transactional
    public Device save(Device device) {
        DeviceEntity entity;
        // (Este método já estava correto)
        if (device.getId() != null) {
            entity = deviceJpaRepository.findById(device.getId())
                .orElseThrow(() -> new EntityNotFoundException("Dispositivo com id " + device.getId() + " não encontrado."));
        } else {
            entity = new DeviceEntity();
        }

        // Atualiza os campos
        entity.setName(device.getName());
        entity.setPosition(device.getPosition());
        entity.setSiteId(device.getSiteId());
        entity.setRackId(device.getRackId());

        if (device.getDeviceModelId() != null) {
            DeviceModelEntity deviceModelEntity = deviceModelJpaRepository.findById(device.getDeviceModelId())
                .orElseThrow(() -> new EntityNotFoundException("Modelo de Dispositivo com id " + device.getDeviceModelId() + " não encontrado ao salvar Device."));
            entity.setDeviceModel(deviceModelEntity);
        } else {
            throw new IllegalArgumentException("ID do Modelo de Dispositivo é obrigatório para salvar Device.");
        }

        DeviceEntity savedEntity = deviceJpaRepository.save(entity);
        return deviceMapper.toModel(savedEntity);
    }

    @Override
    public List<Device> findAllDevices() {
        return deviceJpaRepository.findAll().stream()
                .map(deviceMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Device> findDeviceById(Long id) {
        return deviceJpaRepository.findById(id).map(deviceMapper::toModel);
    }

    @Override
    public List<Device> findBySiteId(Long siteId) {
        return deviceJpaRepository.findBySiteId(siteId).stream()
                .map(deviceMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDeviceById(Long id) {
        deviceJpaRepository.deleteById(id);
    }
}