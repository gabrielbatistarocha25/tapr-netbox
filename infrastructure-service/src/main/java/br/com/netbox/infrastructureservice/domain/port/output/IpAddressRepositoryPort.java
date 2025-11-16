package br.com.netbox.infrastructureservice.domain.port.output;

import br.com.netbox.infrastructureservice.domain.model.IpAddress;
import java.util.List;
import java.util.Optional;

public interface IpAddressRepositoryPort {
    IpAddress save(IpAddress ipAddress);
    Optional<IpAddress> findById(Long id);
    List<IpAddress> findAll();
    List<IpAddress> findByPrefixId(Long prefixId);
    void deleteIpAddressById(Long id);
    boolean ipAddressExistsById(Long id);
}