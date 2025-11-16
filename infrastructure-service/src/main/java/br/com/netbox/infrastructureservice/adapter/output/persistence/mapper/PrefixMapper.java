package br.com.netbox.infrastructureservice.adapter.output.persistence.mapper;

import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.PrefixEntity;
import br.com.netbox.infrastructureservice.domain.model.Prefix;
import org.springframework.stereotype.Component;

@Component
public class PrefixMapper {

    public PrefixEntity toEntity(Prefix model) {
        if (model == null) return null;
        PrefixEntity entity = new PrefixEntity();
        entity.setId(model.getId());
        entity.setNetworkAddress(model.getNetworkAddress());
        entity.setGateway(model.getGateway());
        entity.setDescription(model.getDescription());
        entity.setSiteId(model.getSiteId());
        entity.setVlanId(model.getVlanId());
        return entity;
    }

    public Prefix toModel(PrefixEntity entity) {
        if (entity == null) return null;
        Prefix model = new Prefix();
        model.setId(entity.getId());
        model.setNetworkAddress(entity.getNetworkAddress());
        model.setGateway(entity.getGateway());
        model.setDescription(entity.getDescription());
        model.setSiteId(entity.getSiteId());
        model.setVlanId(entity.getVlanId());
        return model;
    }
}