package br.com.netbox.organizationservice.adapter.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "site")
public class SiteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonBackReference("location-sites")
    private LocationEntity location;

    @OneToMany(mappedBy = "site")
    @JsonManagedReference("site-racks")
    private List<RackEntity> racks;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocationEntity getLocation() { return location; }
    public void setLocation(LocationEntity location) { this.location = location; }
    public List<RackEntity> getRacks() { return racks; }
    public void setRacks(List<RackEntity> racks) { this.racks = racks; }
}