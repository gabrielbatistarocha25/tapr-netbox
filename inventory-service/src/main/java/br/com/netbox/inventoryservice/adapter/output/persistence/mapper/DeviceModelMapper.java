package br.com.netbox.inventoryservice.adapter.output.persistence.mapper;

import br.com.netbox.inventoryservice.adapter.output.persistence.entity.DeviceModelEntity;
import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import org.springframework.stereotype.Component;

@Component
public class DeviceModelMapper {

    private final ManufacturerMapper manufacturerMapper;

    public DeviceModelMapper(ManufacturerMapper manufacturerMapper) {
        this.manufacturerMapper = manufacturerMapper;
    }

    public DeviceModelEntity toEntity(DeviceModel model) {
        if (model == null) return null;
        DeviceModelEntity entity = new DeviceModelEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        if (model.getManufacturer() != null) {
            entity.setManufacturer(manufacturerMapper.toEntity(model.getManufacturer()));
        }
        return entity;
    }

    public DeviceModel toModel(DeviceModelEntity entity) {
        if (entity == null) return null;
        DeviceModel model = new DeviceModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        if (entity.getManufacturer() != null) {
            model.setManufacturer(manufacturerMapper.toModel(entity.getManufacturer()));
        }
        return model;
    }
}