package br.com.netbox.infrastructureservice.domain.model;

public class Prefix {
    private Long id;
    private String networkAddress;
    private String gateway;
    private String description;
    private Long siteId;
    private Long vlanId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNetworkAddress() { return networkAddress; }
    public void setNetworkAddress(String networkAddress) { this.networkAddress = networkAddress; }
    public String getGateway() { return gateway; }
    public void setGateway(String gateway) { this.gateway = gateway; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getSiteId() { return siteId; }
    public void setSiteId(Long siteId) { this.siteId = siteId; }
    public Long getVlanId() { return vlanId; }
    public void setVlanId(Long vlanId) { this.vlanId = vlanId; }
}