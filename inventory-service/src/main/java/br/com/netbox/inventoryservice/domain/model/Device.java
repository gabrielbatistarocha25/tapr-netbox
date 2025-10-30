package br.com.netbox.inventoryservice.domain.model;

public class Device {
    private Long id;
    private String name;
    private Integer position;
    private Long deviceModelId;

    private Long siteId;
    private Long rackId; 

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
    public Long getDeviceModelId() { return deviceModelId; }
    public void setDeviceModelId(Long deviceModelId) { this.deviceModelId = deviceModelId; }
    public Long getSiteId() { return siteId; }
    public void setSiteId(Long siteId) { this.siteId = siteId; }
    public Long getRackId() { return rackId; }
    public void setRackId(Long rackId) { this.rackId = rackId; }
}