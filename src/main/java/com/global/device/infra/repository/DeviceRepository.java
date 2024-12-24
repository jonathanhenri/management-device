package com.global.device.infra.repository;

import com.global.device.infra.model.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceData, Long> {
	
	Optional<DeviceData> findByName(String identifier);
}
