package br.com.netbox.inventoryservice.adapter.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "manufacturer")
public class ManufacturerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("manufacturer-models")
    private List<DeviceModelEntity> deviceModels;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<DeviceModelEntity> getDeviceModels() { return deviceModels; }
    public void setDeviceModels(List<DeviceModelEntity> deviceModels) { this.deviceModels = deviceModels; }
}