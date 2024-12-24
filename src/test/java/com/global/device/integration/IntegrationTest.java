package com.global.device.integration;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class IntegrationTest {
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	private static GenericContainer<?> redisContainer;
	
	@BeforeAll
	public static void setup() {
		// Inicia o container do Redis usando Testcontainers
		redisContainer = new GenericContainer<>("redis:latest").withExposedPorts(6379); // Mapeia a porta 6379
		
		redisContainer.start();  // Inicia o container
		
		// Configura a propriedade spring.redis.port dinamicamente com a porta mapeada pelo Testcontainers
		System.setProperty("spring.redis.port", redisContainer.getFirstMappedPort().toString());
	}
	
	@AfterAll
	public static void tearDown() {
		if (redisContainer != null) {
			redisContainer.stop();
		}
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
	
}
