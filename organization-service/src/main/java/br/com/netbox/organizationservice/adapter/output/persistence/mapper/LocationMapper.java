package br.com.netbox.organizationservice.adapter.output.persistence.mapper;

import br.com.netbox.organizationservice.adapter.output.persistence.entity.LocationEntity;
import br.com.netbox.organizationservice.domain.model.Location;
import org.springframework.context.annotation.Lazy; 
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    private final SiteMapper siteMapper;

    public LocationMapper(@Lazy SiteMapper siteMapper) {
        this.siteMapper = siteMapper;
    }

    public LocationEntity toEntity(Location model) {
        if (model == null) return null;
        LocationEntity entity = new LocationEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setAddress(model.getAddress());
        return entity;
    }

    public Location toModel(LocationEntity entity) {
        if (entity == null) return null;
        Location model = new Location();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setAddress(entity.getAddress());
        if (entity.getSites() != null) {
            model.setSites(entity.getSites().stream()
                .map(siteEntity -> {
                    var siteModel = siteMapper.toModel(siteEntity, false); 
                    siteModel.setLocation(model); 
                    return siteModel;
                })
                .collect(Collectors.toList()));
        }
        return model;
    }
}