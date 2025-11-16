package br.com.netbox.infrastructureservice.domain.service;

import br.com.netbox.infrastructureservice.domain.model.IpAddress;
import br.com.netbox.infrastructureservice.domain.model.Prefix;
import br.com.netbox.infrastructureservice.domain.port.input.IpamUseCase;
import br.com.netbox.infrastructureservice.domain.port.input.NetworkUseCase;
import br.com.netbox.infrastructureservice.domain.port.output.InventoryApiPort;
import br.com.netbox.infrastructureservice.domain.port.output.IpAddressRepositoryPort;
import br.com.netbox.infrastructureservice.domain.port.output.OrganizationApiPort;
import br.com.netbox.infrastructureservice.domain.port.output.PrefixRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpamService implements IpamUseCase {

    private final PrefixRepositoryPort prefixRepository;
    private final IpAddressRepositoryPort ipAddressRepository;
    private final OrganizationApiPort organizationApiPort;
    private final InventoryApiPort inventoryApiPort;
    private final NetworkUseCase networkUseCase;

    public IpamService(PrefixRepositoryPort prefixRepository, IpAddressRepositoryPort ipAddressRepository, OrganizationApiPort organizationApiPort, InventoryApiPort inventoryApiPort, NetworkUseCase networkUseCase) {
        this.prefixRepository = prefixRepository;
        this.ipAddressRepository = ipAddressRepository;
        this.organizationApiPort = organizationApiPort;
        this.inventoryApiPort = inventoryApiPort;
        this.networkUseCase = networkUseCase;
    }

    @Override
    public Prefix createPrefix(Prefix prefix) {
        validatePrefixDependencies(prefix.getSiteId(), prefix.getVlanId());
        return prefixRepository.save(prefix);
    }

    @Override
    public Prefix getPrefixById(Long id) {
        return prefixRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Prefix com id " + id + " não encontrado."));
    }

    @Override
    public List<Prefix> getAllPrefixes() {
        return prefixRepository.findAll();
    }

    @Override
    public List<Prefix> getPrefixesBySite(Long siteId) {
        if (!organizationApiPort.siteExists(siteId)) {
            throw new EntityNotFoundException("Site com id " + siteId + " não encontrado.");
        }
        return prefixRepository.findBySiteId(siteId);
    }

    @Override
    public Prefix updatePrefix(Long id, Prefix prefixUpdate) {
        Prefix existing = getPrefixById(id);
        validatePrefixDependencies(prefixUpdate.getSiteId(), prefixUpdate.getVlanId());
        
        existing.setNetworkAddress(prefixUpdate.getNetworkAddress());
        existing.setGateway(prefixUpdate.getGateway());
        existing.setDescription(prefixUpdate.getDescription());
        existing.setSiteId(prefixUpdate.getSiteId());
        existing.setVlanId(prefixUpdate.getVlanId());
        
        return prefixRepository.save(existing);
    }

    @Override
    public void deletePrefix(Long id) {
        if (!prefixRepository.prefixExistsById(id)) {
            throw new EntityNotFoundException("Prefix com id " + id + " não encontrado.");
        }
        prefixRepository.deletePrefixById(id);
    }

    @Override
    public IpAddress createIpAddress(IpAddress ipAddress) {
        validateIpAddressDependencies(ipAddress.getPrefixId(), ipAddress.getAssignedDeviceId());
        return ipAddressRepository.save(ipAddress);
    }

    @Override
    public IpAddress getIpAddressById(Long id) {
        return ipAddressRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("IPAddress com id " + id + " não encontrado."));
    }

    @Override
    public List<IpAddress> getAllIpAddresses() {
        return ipAddressRepository.findAll();
    }

    @Override
    public List<IpAddress> getIpAddressesByPrefix(Long prefixId) {
        if (!prefixRepository.prefixExistsById(prefixId)) {
            throw new EntityNotFoundException("Prefix com id " + prefixId + " não encontrado.");
        }
        return ipAddressRepository.findByPrefixId(prefixId);
    }

    @Override
    public IpAddress updateIpAddress(Long id, IpAddress ipAddressUpdate) {
        IpAddress existing = getIpAddressById(id);
        validateIpAddressDependencies(ipAddressUpdate.getPrefixId(), ipAddressUpdate.getAssignedDeviceId());

        existing.setAddress(ipAddressUpdate.getAddress());
        existing.setDescription(ipAddressUpdate.getDescription());
        existing.setPrefixId(ipAddressUpdate.getPrefixId());
        existing.setAssignedDeviceId(ipAddressUpdate.getAssignedDeviceId());

        return ipAddressRepository.save(existing);
    }

    @Override
    public void deleteIpAddress(Long id) {
        if (!ipAddressRepository.ipAddressExistsById(id)) {
            throw new EntityNotFoundException("IPAddress com id " + id + " não encontrado.");
        }
        ipAddressRepository.deleteIpAddressById(id);
    }

    private void validatePrefixDependencies(Long siteId, Long vlanId) {
        if (siteId == null) {
            throw new IllegalArgumentException("O ID do Site é obrigatório para o Prefix.");
        }
        if (!organizationApiPort.siteExists(siteId)) {
            throw new EntityNotFoundException("Site com id " + siteId + " não encontrado.");
        }
        if (vlanId != null && !networkUseCase.vlanExists(vlanId)) {
            throw new EntityNotFoundException("VLAN com id " + vlanId + " não encontrada.");
        }
    }

    private void validateIpAddressDependencies(Long prefixId, Long deviceId) {
        if (prefixId == null) {
            throw new IllegalArgumentException("O ID do Prefix é obrigatório para o IPAddress.");
        }
        if (!prefixRepository.prefixExistsById(prefixId)) {
            throw new EntityNotFoundException("Prefix com id " + prefixId + " não encontrado.");
        }
        if (deviceId != null && !inventoryApiPort.deviceExists(deviceId)) {
            throw new EntityNotFoundException("Device com id " + deviceId + " não encontrado.");
        }
    }
}