package br.com.netbox.infrastructureservice.domain.port.input;

import br.com.netbox.infrastructureservice.domain.model.IpAddress;
import br.com.netbox.infrastructureservice.domain.model.Prefix;
import java.util.List;

public interface IpamUseCase {
    Prefix createPrefix(Prefix prefix);
    Prefix getPrefixById(Long id);
    List<Prefix> getAllPrefixes();
    List<Prefix> getPrefixesBySite(Long siteId);
    Prefix updatePrefix(Long id, Prefix prefix);
    void deletePrefix(Long id);

    IpAddress createIpAddress(IpAddress ipAddress);
    IpAddress getIpAddressById(Long id);
    List<IpAddress> getAllIpAddresses();
    List<IpAddress> getIpAddressesByPrefix(Long prefixId);
    IpAddress updateIpAddress(Long id, IpAddress ipAddress);
    void deleteIpAddress(Long id);
}