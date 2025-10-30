package br.com.netbox.organizationservice.adapter.output.persistence.repository;

import br.com.netbox.organizationservice.adapter.output.persistence.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationJpaRepository extends JpaRepository<LocationEntity, Long> {}