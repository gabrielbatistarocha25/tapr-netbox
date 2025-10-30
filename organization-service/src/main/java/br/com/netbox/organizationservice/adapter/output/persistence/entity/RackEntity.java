package br.com.netbox.organizationservice.adapter.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "rack")
public class RackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int uHeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    @JsonBackReference("site-racks")
    private SiteEntity site;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getuHeight() { return uHeight; }
    public void setuHeight(int uHeight) { this.uHeight = uHeight; }
    public SiteEntity getSite() { return site; }
    public void setSite(SiteEntity site) { this.site = site; }
}