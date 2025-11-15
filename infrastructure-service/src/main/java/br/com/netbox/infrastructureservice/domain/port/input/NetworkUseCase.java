package br.com.netbox.infrastructureservice.domain.port.input;

import br.com.netbox.infrastructureservice.domain.model.Vlan;
import java.util.List;

public interface NetworkUseCase {
    Vlan createVlan(Vlan vlan);
    List<Vlan> getAllVlans();
    List<Vlan> getVlansBySite(Long siteId);
    Vlan getVlanById(Long id); 
    Vlan updateVlan(Long id, Vlan vlan); 
    void deleteVlan(Long id); 
}