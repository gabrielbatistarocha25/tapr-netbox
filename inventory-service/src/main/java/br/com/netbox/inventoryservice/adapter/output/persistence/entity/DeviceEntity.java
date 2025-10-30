package br.com.netbox.inventoryservice.adapter.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "device")
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer position;

    @Column(name = "site_id")
    private Long siteId;

    @Column(name = "rack_id")
    private Long rackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "devicemodel_id")
    @JsonBackReference("model-devices")
    private DeviceModelEntity deviceModel;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
    public Long getSiteId() { return siteId; }
    public void setSiteId(Long siteId) { this.siteId = siteId; }
    public Long getRackId() { return rackId; }
    public void setRackId(Long rackId) { this.rackId = rackId; }
    public DeviceModelEntity getDeviceModel() { return deviceModel; }
    public void setDeviceModel(DeviceModelEntity deviceModel) { this.deviceModel = deviceModel; }
}