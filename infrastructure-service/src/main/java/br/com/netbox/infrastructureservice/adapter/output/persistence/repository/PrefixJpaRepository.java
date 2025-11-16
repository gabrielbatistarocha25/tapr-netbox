package br.com.netbox.infrastructureservice.adapter.output.persistence.repository;

import br.com.netbox.infrastructureservice.adapter.output.persistence.entity.PrefixEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrefixJpaRepository extends JpaRepository<PrefixEntity, Long> {
    List<PrefixEntity> findBySiteId(Long siteId);
}