package br.com.netbox.infrastructureservice.adapter.output.rest;

import br.com.netbox.infrastructureservice.domain.port.output.InventoryApiPort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryRestAdapter implements InventoryApiPort {

    private final RestTemplate restTemplate;
    private final String inventoryServiceName = "inventory-service";

    public InventoryRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean deviceExists(Long deviceId) {
        try {
            String url = "http://" + inventoryServiceName + "/api/devices/" + deviceId;
            restTemplate.getForEntity(url, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}