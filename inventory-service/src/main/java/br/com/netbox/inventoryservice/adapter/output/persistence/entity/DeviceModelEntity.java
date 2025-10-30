package br.com.netbox.inventoryservice.adapter.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "device_model")
public class DeviceModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    @JsonBackReference("manufacturer-models")
    private ManufacturerEntity manufacturer;

    @OneToMany(mappedBy = "deviceModel")
    @JsonManagedReference("model-devices")
    private List<DeviceEntity> devices;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ManufacturerEntity getManufacturer() { return manufacturer; }
    public void setManufacturer(ManufacturerEntity manufacturer) { this.manufacturer = manufacturer; }
    public List<DeviceEntity> getDevices() { return devices; }
    public void setDevices(List<DeviceEntity> devices) { this.devices = devices; }
}