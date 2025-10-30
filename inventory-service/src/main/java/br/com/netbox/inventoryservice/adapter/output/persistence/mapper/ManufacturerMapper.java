package br.com.netbox.inventoryservice.adapter.output.persistence.mapper;

import br.com.netbox.inventoryservice.adapter.output.persistence.entity.ManufacturerEntity;
import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerMapper {

    public ManufacturerEntity toEntity(Manufacturer model) {
        if (model == null) return null;
        ManufacturerEntity entity = new ManufacturerEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        return entity;
    }

    public Manufacturer toModel(ManufacturerEntity entity) {
        if (entity == null) return null;
        Manufacturer model = new Manufacturer();
        model.setId(entity.getId());
        model.setName(entity.getName());
        return model;
    }
}