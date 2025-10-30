package br.com.netbox.organizationservice.adapter.output.persistence.mapper;

import br.com.netbox.organizationservice.adapter.output.persistence.entity.SiteEntity;
import br.com.netbox.organizationservice.domain.model.Site;
import org.springframework.context.annotation.Lazy; 
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class SiteMapper {

    private final LocationMapper locationMapper;
    private final RackMapper rackMapper;

    public SiteMapper(LocationMapper locationMapper, @Lazy RackMapper rackMapper) {
        this.locationMapper = locationMapper;
        this.rackMapper = rackMapper;
    }

    public SiteEntity toEntity(Site model) {
        if (model == null) return null;
        SiteEntity entity = new SiteEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        if (model.getLocation() != null) {
            entity.setLocation(locationMapper.toEntity(model.getLocation()));
        }
        return entity;
    }

    public Site toModel(SiteEntity entity) {
        return toModel(entity, true); 
    }

    public Site toModel(SiteEntity entity, boolean mapLocation) {
        if (entity == null) return null;
        Site model = new Site();
        model.setId(entity.getId());
        model.setName(entity.getName());

        if (mapLocation && entity.getLocation() != null) {
            model.setLocation(locationMapper.toModel(entity.getLocation()));
        }

        if (entity.getRacks() != null) {
            model.setRacks(entity.getRacks().stream()
                .map(rackEntity -> {
                    var rackModel = rackMapper.toModel(rackEntity, false); 
                    rackModel.setSite(model); 
                    return rackModel;
                })
                .collect(Collectors.toList()));
        }
        return model;
    }
}