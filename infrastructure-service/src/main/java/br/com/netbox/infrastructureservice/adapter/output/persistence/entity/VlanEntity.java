package br.com.netbox.infrastructureservice.adapter.output.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vlan")
public class VlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer vlanId;
    private String name;

    @Column(name = "site_id")
    private Long siteId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getVlanId() { return vlanId; }
    public void setVlanId(Integer vlanId) { this.vlanId = vlanId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getSiteId() { return siteId; }
    public void setSiteId(Long siteId) { this.siteId = siteId; }
}