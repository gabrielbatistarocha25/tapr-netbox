package br.com.netbox.infrastructureservice.adapter.output.persistence.repository;

import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.IpAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IpAddressJpaRepository extends JpaRepository<IpAddressEntity, Long> {
    List<IpAddressEntity> findByPrefixId(Long prefixId);
}