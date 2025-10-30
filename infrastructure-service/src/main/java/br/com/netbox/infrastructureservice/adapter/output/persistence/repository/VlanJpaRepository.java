package br.com.netbox.infrastructureservice.adapter.output.persistence.repository;

import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.VlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VlanJpaRepository extends JpaRepository<VlanEntity, Long> {

    List<VlanEntity> findBySiteId(Long siteId);
}