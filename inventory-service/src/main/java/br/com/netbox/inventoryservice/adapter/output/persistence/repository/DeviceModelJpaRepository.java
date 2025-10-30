package br.com.netbox.inventoryservice.adapter.output.persistence.repository;

import br.com.netbox.inventoryservice.adapter.output.persistence.entity.DeviceModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceModelJpaRepository extends JpaRepository<DeviceModelEntity, Long> {}