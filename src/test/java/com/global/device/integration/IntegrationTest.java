package com.global.device.integration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.global.device.BaseIntegrationTest;
import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceService;
import com.global.device.config.TestRedisConfig;
import com.global.device.domain.exception.DomainException;
import com.global.device.infra.model.DeviceData;
import com.global.device.infra.repository.DeviceRepository;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.SoftAssertions;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import redis.clients.jedis.Jedis;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestRedisConfig.class)
public class IntegrationTest extends BaseIntegrationTest {
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@AfterEach
	public void cleanUp() {
		deviceRepository.deleteAll();
	}
	
	
	@Autowired
	private DeviceService deviceService;
	
	@Test
	void shoudCreateDevice() {
		DeviceRecord deviceRecordToCreate = new DeviceRecord("macbook", "apple");
		deviceService.createDevice(deviceRecordToCreate);
		Optional<DeviceData> optionalDeviceData = deviceRepository.findByName("macbook");
		
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(optionalDeviceData.isPresent()).isTrue();
			DeviceData deviceData = optionalDeviceData.get();
			
			softAssertions.assertThat(deviceData.getName()).isEqualTo(deviceRecordToCreate.name());
			softAssertions.assertThat(deviceData.getBrand()).isEqualTo(deviceRecordToCreate.brand());
		});
	}
	
	@Test
	void exceptionCreateDevice() {
		DeviceRecord deviceRecordToCreate = new DeviceRecord("macbook", null);
		assertThrows(DomainException.class, () -> deviceService.createDevice(deviceRecordToCreate));
	}
	
	@Test
	void getDeviceByIdentifier() {
		DeviceRecord deviceRecordToCreate = new DeviceRecord("macbook", "apple");
		deviceService.createDevice(deviceRecordToCreate);
		
		DeviceRecord deviceRecordCreated = deviceService.getDeviceByIdentifier("macbook");
		
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(deviceRecordCreated).isNotNull();
			softAssertions.assertThat(deviceRecordCreated.name()).isEqualTo(deviceRecordToCreate.name());
			softAssertions.assertThat(deviceRecordCreated.brand()).isEqualTo(deviceRecordToCreate.brand());
		});
	}
	
	@Test
	void shouldReturnAllDevices() {
		DeviceRecord deviceRecordToCreate1 = new DeviceRecord("macbook", "apple");
		deviceService.createDevice(deviceRecordToCreate1);
		
		DeviceRecord deviceRecordToCreate2 = new DeviceRecord("macbook2", "apple2");
		deviceService.createDevice(deviceRecordToCreate2);
		
		List<DeviceRecord> listRecords = deviceService.listAllDevices();
		
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(listRecords).isNotNull();
			softAssertions.assertThat(listRecords.size()).isEqualTo(2);
			softAssertions.assertThat(listRecords.get(0).name()).isEqualTo(deviceRecordToCreate1.name());
			softAssertions.assertThat(listRecords.get(0).brand()).isEqualTo(deviceRecordToCreate1.brand());
			
			softAssertions.assertThat(listRecords.get(1).name()).isEqualTo(deviceRecordToCreate2.name());
			softAssertions.assertThat(listRecords.get(1).brand()).isEqualTo(deviceRecordToCreate2.brand());
		});
	}
	
	@Test
	void testUpdateFull() {
		DeviceRecord deviceRecordToCreate = new DeviceRecord("macbook", "apple");
		deviceService.createDevice(deviceRecordToCreate);
		
		DeviceRecord deviceRecordToUpdate = new DeviceRecord("macbook", "google");
		
		DeviceRecord deviceRecordUpdated = deviceService.updateDevice(deviceRecordToUpdate.name(), deviceRecordToUpdate);
		
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(deviceRecordUpdated).isNotNull();
			softAssertions.assertThat(deviceRecordUpdated.name()).isEqualTo(deviceRecordToUpdate.name());
			softAssertions.assertThat(deviceRecordUpdated.brand()).isEqualTo(deviceRecordToUpdate.brand());
		});
	}
	
	@Test
	void testDelete() {
		DeviceRecord deviceRecordToCreate = new DeviceRecord("macbook", "apple");
		deviceService.createDevice(deviceRecordToCreate);
		assertTrue(deviceService.deleteDevice(deviceRecordToCreate.name()));
	}
	
	@Test
	void getDeviceByBrand() {
		DeviceRecord deviceRecordToCreate = new DeviceRecord("macbook", "apple");
		deviceService.createDevice(deviceRecordToCreate);
		
		List<DeviceRecord> deviceRecordList = deviceService.getDeviceByBrand(deviceRecordToCreate.brand());
		
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(deviceRecordList).isNotNull();
			softAssertions.assertThat(deviceRecordList.size()).isEqualTo(1);
			softAssertions.assertThat(deviceRecordList.get(0).name()).isEqualTo(deviceRecordToCreate.name());
			softAssertions.assertThat(deviceRecordList.get(0).brand()).isEqualTo(deviceRecordToCreate.brand());
		});
	}
	
}
