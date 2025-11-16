package br.com.netbox.infrastructureservice.adapter.output.persistence.mapper;

import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.IpAddressEntity;
import br.com.netbox.infrastructureservice.domain.model.IpAddress;
import org.springframework.stereotype.Component;

@Component
public class IpAddressMapper {

    public IpAddressEntity toEntity(IpAddress model) {
        if (model == null) return null;
        IpAddressEntity entity = new IpAddressEntity();
        entity.setId(model.getId());
        entity.setAddress(model.getAddress());
        entity.setDescription(model.getDescription());
        entity.setAssignedDeviceId(model.getAssignedDeviceId());
        return entity;
    }

    public IpAddress toModel(IpAddressEntity entity) {
        if (entity == null) return null;
        IpAddress model = new IpAddress();
        model.setId(entity.getId());
        model.setAddress(entity.getAddress());
        model.setDescription(entity.getDescription());
        model.setAssignedDeviceId(entity.getAssignedDeviceId());
        if (entity.getPrefix() != null) {
            model.setPrefixId(entity.getPrefix().getId());
        }
        return model;
    }
}