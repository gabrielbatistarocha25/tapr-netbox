package br.com.netbox.infrastructureservice.domain.service;

import br.com.netbox.infrastructureservice.domain.model.Vlan;
import br.com.netbox.infrastructureservice.domain.port.input.NetworkUseCase;
import br.com.netbox.infrastructureservice.domain.port.output.OrganizationApiPort;
import br.com.netbox.infrastructureservice.domain.port.output.VlanRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkService implements NetworkUseCase {

    private final VlanRepositoryPort vlanRepositoryPort;
    private final OrganizationApiPort organizationApiPort;

    public NetworkService(VlanRepositoryPort vlanRepositoryPort, OrganizationApiPort organizationApiPort) {
        this.vlanRepositoryPort = vlanRepositoryPort;
        this.organizationApiPort = organizationApiPort;
    }

    @Override
    public Vlan createVlan(Vlan vlan) {
        validateVlanDependencies(vlan.getSiteId());
        return vlanRepositoryPort.save(vlan);
    }

    @Override
    public List<Vlan> getAllVlans() {
        return vlanRepositoryPort.findAll();
    }

    @Override
    public List<Vlan> getVlansBySite(Long siteId) {
        if (!organizationApiPort.siteExists(siteId)) {
            throw new EntityNotFoundException("Site com id " + siteId + " não encontrado.");
        }
        return vlanRepositoryPort.findBySiteId(siteId);
    }

    @Override
    public Vlan getVlanById(Long id) {
        return vlanRepositoryPort.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("VLAN com id " + id + " não encontrada."));
    }

    @Override
    public Vlan updateVlan(Long id, Vlan vlanUpdate) {
        Vlan existing = getVlanById(id);
        validateVlanDependencies(vlanUpdate.getSiteId());
        
        existing.setName(vlanUpdate.getName());
        existing.setVlanId(vlanUpdate.getVlanId());
        existing.setSiteId(vlanUpdate.getSiteId());

        return vlanRepositoryPort.save(existing);
    }

    @Override
    public void deleteVlan(Long id) {
        getVlanById(id);
        vlanRepositoryPort.deleteById(id);
    }
    
    @Override
    public boolean vlanExists(Long id) {
        return vlanRepositoryPort.existsById(id);
    }

    private void validateVlanDependencies(Long siteId) {
        if (siteId == null) {
            throw new IllegalArgumentException("O ID do Site é obrigatório para criar uma VLAN.");
        }
        if (!organizationApiPort.siteExists(siteId)) {
            throw new EntityNotFoundException("Site com id " + siteId + " não encontrado.");
        }
    }
}