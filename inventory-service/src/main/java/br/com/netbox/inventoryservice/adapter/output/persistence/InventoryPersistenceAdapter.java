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
import jakarta.persistence.EntityNotFoundException; // Importar
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // Importar

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
    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        ManufacturerEntity entity = manufacturerMapper.toEntity(manufacturer);
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

    // --- DeviceModelRepositoryPort Methods ---
    @Override
    @Transactional // Adicionar @Transactional para garantir consistência
    public DeviceModel save(DeviceModel deviceModel) {
        DeviceModelEntity entity = deviceModelMapper.toEntity(deviceModel);
        // Garante que o fabricante (interno) está gerenciado
        if (deviceModel.getManufacturer() != null && deviceModel.getManufacturer().getId() != null) {
            ManufacturerEntity manufacturerEntity = manufacturerJpaRepository.findById(deviceModel.getManufacturer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Fabricante com id " + deviceModel.getManufacturer().getId() + " não encontrado ao salvar DeviceModel."));
            entity.setManufacturer(manufacturerEntity);
        } else {
             throw new IllegalArgumentException("ID do Fabricante é obrigatório para salvar DeviceModel.");
        }
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

    // --- DeviceRepositoryPort Methods ---
    @Override
    @Transactional // Adicionar @Transactional
    public Device save(Device device) {
        DeviceEntity entity = deviceMapper.toEntity(device); // Converte para entidade (sem DeviceModel associado ainda)

        // Busca a DeviceModelEntity usando o ID fornecido no Device model
        if (device.getDeviceModelId() != null) {
            DeviceModelEntity deviceModelEntity = deviceModelJpaRepository.findById(device.getDeviceModelId())
                .orElseThrow(() -> new EntityNotFoundException("Modelo de Dispositivo com id " + device.getDeviceModelId() + " não encontrado ao salvar Device."));
            entity.setDeviceModel(deviceModelEntity); // Associa a entidade encontrada
        } else {
            throw new IllegalArgumentException("ID do Modelo de Dispositivo é obrigatório para salvar Device.");
        }

        // Salva a entidade DeviceEntity com a DeviceModelEntity associada
        DeviceEntity savedEntity = deviceJpaRepository.save(entity);
        return deviceMapper.toModel(savedEntity); // Converte de volta para o modelo de domínio
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
}