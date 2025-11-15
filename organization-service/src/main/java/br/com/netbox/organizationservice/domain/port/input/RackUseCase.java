package br.com.netbox.organizationservice.domain.port.input;

import br.com.netbox.organizationservice.domain.model.Rack;
import java.util.List;

public interface RackUseCase {
    Rack createRack(Rack rack);
    List<Rack> getAllRacks();
    Rack getRackById(Long id);
    Rack updateRack(Long id, Rack rack);
    void deleteRack(Long id); 
}