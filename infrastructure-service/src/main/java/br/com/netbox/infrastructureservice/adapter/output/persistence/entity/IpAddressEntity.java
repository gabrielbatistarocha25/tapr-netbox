package br.com.netbox.infrastructureservice.adapter.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "ip_address")
public class IpAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String address;
    
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prefix_id", nullable = false)
    @JsonBackReference
    private PrefixEntity prefix;

    @Column(name = "assigned_device_id")
    private Long assignedDeviceId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public PrefixEntity getPrefix() { return prefix; }
    public void setPrefix(PrefixEntity prefix) { this.prefix = prefix; }
    public Long getAssignedDeviceId() { return assignedDeviceId; }
    public void setAssignedDeviceId(Long assignedDeviceId) { this.assignedDeviceId = assignedDeviceId; }
}