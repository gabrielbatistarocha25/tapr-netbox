package br.com.netbox.organizationservice.domain.service;

import br.com.netbox.organizationservice.domain.model.Rack;
import br.com.netbox.organizationservice.domain.model.Site;
import br.com.netbox.organizationservice.domain.port.input.RackUseCase;
import br.com.netbox.organizationservice.domain.port.output.RackRepositoryPort;
import br.com.netbox.organizationservice.domain.port.output.SiteRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RackService implements RackUseCase {

    private final RackRepositoryPort rackRepositoryPort;
    private final SiteRepositoryPort siteRepositoryPort;

    public RackService(RackRepositoryPort rackRepositoryPort, SiteRepositoryPort siteRepositoryPort) {
        this.rackRepositoryPort = rackRepositoryPort;
        this.siteRepositoryPort = siteRepositoryPort;
    }

    @Override
    public Rack createRack(Rack rack) {
        if (rack.getSite() == null || rack.getSite().getId() == null) {
            throw new IllegalArgumentException("O ID do Site é obrigatório para criar um Rack.");
        }

        Site site = siteRepositoryPort.findSiteById(rack.getSite().getId())
                .orElseThrow(() -> new EntityNotFoundException("Site com id " + rack.getSite().getId() + " não encontrado."));

        rack.setSite(site);
        return rackRepositoryPort.save(rack);
    }

    @Override
    public List<Rack> getAllRacks() {
        return rackRepositoryPort.findAllRacks();
    }

    @Override
    public Rack getRackById(Long id) {
         return rackRepositoryPort.findRackById(id)
            .orElseThrow(() -> new EntityNotFoundException("Rack com id " + id + " não encontrado."));
    }

    @Override
    public Rack updateRack(Long id, Rack rackUpdate) {
        Rack existingRack = getRackById(id);

        if (rackUpdate.getSite() == null || rackUpdate.getSite().getId() == null) {
            throw new IllegalArgumentException("O ID do Site é obrigatório para atualizar o Rack.");
        }

        Site site = siteRepositoryPort.findSiteById(rackUpdate.getSite().getId())
                .orElseThrow(() -> new EntityNotFoundException("Site com id " + rackUpdate.getSite().getId() + " não encontrado."));

        existingRack.setName(rackUpdate.getName());
        existingRack.setuHeight(rackUpdate.getuHeight());
        existingRack.setSite(site);

        return rackRepositoryPort.save(existingRack);
    }

    @Override
    public void deleteRack(Long id) {
        if (!rackRepositoryPort.rackExistsById(id)) {
             throw new EntityNotFoundException("Rack com id " + id + " não encontrado.");
        }
        rackRepositoryPort.deleteRackById(id);
    }
}