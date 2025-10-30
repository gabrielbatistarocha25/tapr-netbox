package br.com.netbox.organizationservice.adapter.output.persistence.mapper;

import br.com.netbox.organizationservice.adapter.output.persistence.entity.RackEntity;
import br.com.netbox.organizationservice.domain.model.Rack;
import org.springframework.stereotype.Component;

@Component
public class RackMapper {

    private final SiteMapper siteMapper;

    public RackMapper(SiteMapper siteMapper) {
         this.siteMapper = siteMapper;
    }

    public RackEntity toEntity(Rack model) {
        if (model == null) return null;
        RackEntity entity = new RackEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setuHeight(model.getuHeight());
        if (model.getSite() != null) {
            entity.setSite(siteMapper.toEntity(model.getSite()));
        }
        return entity;
    }

    public Rack toModel(RackEntity entity) {
        return toModel(entity, true);
    }

    public Rack toModel(RackEntity entity, boolean mapSite) {
        if (entity == null) return null;
        Rack model = new Rack();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setuHeight(entity.getuHeight());

        if (mapSite && entity.getSite() != null) {
            model.setSite(siteMapper.toModel(entity.getSite(), false));
        }
        return model;
    }
}