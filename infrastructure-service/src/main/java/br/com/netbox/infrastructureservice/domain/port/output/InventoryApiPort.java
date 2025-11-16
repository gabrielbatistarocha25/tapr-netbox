package br.com.netbox.infrastructureservice.domain.port.output;

public interface InventoryApiPort {
    boolean deviceExists(Long deviceId);
}