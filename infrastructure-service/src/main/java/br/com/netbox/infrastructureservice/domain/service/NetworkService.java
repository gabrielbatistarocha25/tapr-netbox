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
        // Lógica do NetworkService original
        if (vlan.getSiteId() == null) {
            throw new IllegalArgumentException("O ID do Site é obrigatório para criar uma VLAN.");
        }

        // Validação externa (Microserviço)
        if (!organizationApiPort.siteExists(vlan.getSiteId())) {
            throw new EntityNotFoundException("Site com id " + vlan.getSiteId() + " não encontrado.");
        }

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
}