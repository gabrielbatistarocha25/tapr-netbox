package br.com.netbox.infrastructureservice.adapter.output.persistence.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "prefix")
public class PrefixEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String networkAddress;
    
    private String gateway;
    private String description;

    @Column(nullable = false)
    private Long siteId;

    @Column(name = "vlan_id")
    private Long vlanId;

    @OneToMany(mappedBy = "prefix", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IpAddressEntity> ipAddresses;

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
    public List<IpAddressEntity> getIpAddresses() { return ipAddresses; }
    public void setIpAddresses(List<IpAddressEntity> ipAddresses) { this.ipAddresses = ipAddresses; }
}