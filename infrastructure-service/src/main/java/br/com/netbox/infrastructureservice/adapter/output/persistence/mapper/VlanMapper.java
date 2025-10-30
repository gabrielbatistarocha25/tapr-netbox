package br.com.netbox.infrastructureservice.adapter.output.persistence.mapper;

import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.VlanEntity;
import br.com.netbox.infrastructureservice.domain.model.Vlan;
import org.springframework.stereotype.Component;

@Component
public class VlanMapper {

    public VlanEntity toEntity(Vlan model) {
        if (model == null) return null;
        VlanEntity entity = new VlanEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setVlanId(model.getVlanId());
        entity.setSiteId(model.getSiteId());
        return entity;
    }

    public Vlan toModel(VlanEntity entity) {
        if (entity == null) return null;
        Vlan model = new Vlan();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setVlanId(entity.getVlanId());
        model.setSiteId(entity.getSiteId());
        return model;
    }
}