package br.com.netbox.organizationservice.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Site {
    private Long id;
    private String name;
    @JsonBackReference("location-sites")
    private Location location;
    @JsonManagedReference("site-racks") 
    private List<Rack> racks;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public List<Rack> getRacks() { return racks; }
    public void setRacks(List<Rack> racks) { this.racks = racks; }
}