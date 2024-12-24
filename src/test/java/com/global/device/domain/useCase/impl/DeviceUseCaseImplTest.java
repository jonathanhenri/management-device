package com.global.device.domain.useCase.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.global.device.domain.DeviceProvider;
import com.global.device.domain.entity.Device;
import com.global.device.domain.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DeviceUseCaseImplTest {
	
	@Mock
	private DeviceProvider deviceProvider; // Mock do DeviceProvider
	
	@InjectMocks
	private DeviceUseCaseImpl deviceUseCaseImpl; // Instancia o DeviceUseCaseImpl com o mock do deviceProvider
	
	private Device validDevice;
	private Device invalidDevice;
	
	@BeforeEach
	void setUp() {
		// Configuração dos dispositivos para os testes
		validDevice = new Device();
		validDevice.setName("Device 1");
		validDevice.setBrand("Brand A");
		validDevice.setCreateTime(LocalDateTime.now());
		
		invalidDevice = new Device();
		invalidDevice.setName(null); // Dispositivo inválido sem nome
	}
	
	@Test
	void testCreateDevice_success() {
		// Arrange
		when(deviceProvider.createDevice(validDevice)).thenReturn(validDevice);
		
		// Act
		Device result = deviceUseCaseImpl.createDevice(validDevice);
		
		// Assert
		assertNotNull(result);
		assertEquals(validDevice.getName(), result.getName());
		assertEquals(validDevice.getBrand(), result.getBrand());
		verify(deviceProvider, times(1)).createDevice(validDevice); // Verifica que o método foi chamado uma vez
	}
	
	@Test
	void testCreateDevice_throwsException_whenDeviceIsInvalid() {
		// Act & Assert
		DomainException exception = assertThrows(DomainException.class, () -> {
			deviceUseCaseImpl.createDevice(invalidDevice);
		});
		
		assertEquals("Device not completed", exception.getMessage());
		verify(deviceProvider, times(0)).createDevice(invalidDevice); // Verifica que o método não foi chamado
	}
	
	@Test
	void testGetDeviceByIdentifier() {
		// Arrange
		String identifier = "1234";
		when(deviceProvider.getDeviceByIdentifier(identifier)).thenReturn(validDevice);
		
		// Act
		Device result = deviceUseCaseImpl.getDeviceByIdentifier(identifier);
		
		// Assert
		assertNotNull(result);
		assertEquals(validDevice, result);
		verify(deviceProvider, times(1)).getDeviceByIdentifier(identifier);
	}
	
	@Test
	void testListAllDevices() {
		// Arrange
		List<Device> devices = Arrays.asList(validDevice, validDevice);
		when(deviceProvider.listAllDevices()).thenReturn(devices);
		
		// Act
		List<Device> result = deviceUseCaseImpl.listAllDevices();
		
		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());
		verify(deviceProvider, times(1)).listAllDevices();
	}
	
	@Test
	void testUpdateDevice() {
		// Arrange
		String identifier = "1234";
		when(deviceProvider.updateDevice(eq(identifier), any(Device.class))).thenReturn(validDevice);
		
		// Act
		Device result = deviceUseCaseImpl.updateDevice(identifier, validDevice);
		
		// Assert
		assertNotNull(result);
		assertEquals(validDevice, result);
		verify(deviceProvider, times(1)).updateDevice(eq(identifier), any(Device.class));
	}
	
	@Test
	void testDeleteDevice() {
		// Arrange
		String identifier = "1234";
		when(deviceProvider.deleteDevice(identifier)).thenReturn(true);
		
		// Act
		boolean result = deviceUseCaseImpl.deleteDevice(identifier);
		
		// Assert
		assertTrue(result);
		verify(deviceProvider, times(1)).deleteDevice(identifier);
	}
	
	@Test
	void testGetDeviceByBrand() {
		// Arrange
		String brand = "Brand A";
		when(deviceProvider.getDeviceByBrand(brand)).thenReturn(List.of(validDevice));
		
		// Act
		List<Device> result = deviceUseCaseImpl.getDeviceByBrand(brand);
		
		// Assert
		assertNotNull(result);
		assertEquals(validDevice, result.get(0));
		verify(deviceProvider, times(1)).getDeviceByBrand(brand);
	}
	
}
