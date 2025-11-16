package br.com.netbox.infrastructureservice.domain.model;

public class IpAddress {
    private Long id;
    private String address;
    private String description;
    private Long prefixId;
    private Long assignedDeviceId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getPrefixId() { return prefixId; }
    public void setPrefixId(Long prefixId) { this.prefixId = prefixId; }
    public Long getAssignedDeviceId() { return assignedDeviceId; }
    public void setAssignedDeviceId(Long assignedDeviceId) { this.assignedDeviceId = assignedDeviceId; }
}