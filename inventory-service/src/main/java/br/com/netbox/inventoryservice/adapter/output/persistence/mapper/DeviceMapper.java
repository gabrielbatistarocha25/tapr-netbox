package br.com.netbox.inventoryservice.adapter.output.persistence.mapper;

import br.com.netbox.inventoryservice.adapter.output.persistence.entity.DeviceEntity;
import br.com.netbox.inventoryservice.domain.model.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    private final DeviceModelMapper deviceModelMapper;

    public DeviceMapper(DeviceModelMapper deviceModelMapper) {
        this.deviceModelMapper = deviceModelMapper;
    }

    public DeviceEntity toEntity(Device model) {
        if (model == null) return null;
        DeviceEntity entity = new DeviceEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setPosition(model.getPosition());
        entity.setSiteId(model.getSiteId());
        entity.setRackId(model.getRackId());

        return entity;
    }

    public Device toModel(DeviceEntity entity) {
        if (entity == null) return null;
        Device model = new Device();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPosition(entity.getPosition());
        model.setSiteId(entity.getSiteId());
        model.setRackId(entity.getRackId());

        if (entity.getDeviceModel() != null) {
             model.setDeviceModelId(entity.getDeviceModel().getId());
        }
        return model;
    }
}