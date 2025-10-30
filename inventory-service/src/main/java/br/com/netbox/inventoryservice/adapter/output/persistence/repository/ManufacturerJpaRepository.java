package br.com.netbox.inventoryservice.adapter.output.persistence.repository;

import br.com.netbox.inventoryservice.adapter.output.persistence.entity.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerJpaRepository extends JpaRepository<ManufacturerEntity, Long> {}