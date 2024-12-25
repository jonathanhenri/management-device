package com.global.device.infra.repository;

import com.global.device.infra.model.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceData, Long> {
	
	Optional<DeviceData> findByIdentifier(String identifier);
	
	List<DeviceData> findAllByBrand(String brand);
	
	@Modifying
	@Query("DELETE FROM DeviceData d WHERE d.identifier = :identifier")
	int deleteByIdentifier(@Param("identifier") String identifier);
	
}
