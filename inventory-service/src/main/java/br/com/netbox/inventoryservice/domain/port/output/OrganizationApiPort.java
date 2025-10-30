package br.com.netbox.inventoryservice.domain.port.output;

public interface OrganizationApiPort {
    boolean siteExists(Long siteId);
    boolean rackExists(Long rackId);
}