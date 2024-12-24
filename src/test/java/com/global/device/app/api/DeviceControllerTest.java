package com.global.device.app.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.global.device.BaseIntegrationTest;
import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceService;
import com.global.device.infra.repository.DeviceRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DeviceControllerTest extends BaseIntegrationTest {
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private CacheManager cacheManager;
	
	@AfterEach
	public void cleanUp() {
		deviceRepository.deleteAll();
		cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}
	
	@Test
	void shouldCreateDeviceSuccessfully() throws Exception {
		DeviceRecord deviceRequest = new DeviceRecord("DeviceName", "BrandName");
		
		mockMvc.perform(
						post("/devices/create").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(deviceRequest)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("DeviceName"))
				.andExpect(jsonPath("$.brand").value("BrandName"));
	}
	
	@Test
	void shouldReturnDeviceWhenFound() throws Exception {
		String identifier = "device123";
		DeviceRecord deviceRecord = new DeviceRecord("device123", "Device Brand");
		
		deviceService.createDevice(deviceRecord);
		
		mockMvc.perform(
						get("/devices/findByIdentifier/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value(deviceRecord.name()))
				.andExpect(jsonPath("$.brand").value(deviceRecord.brand()));
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceNotFound() throws Exception {
		String identifier = "device123";
		
		mockMvc.perform(
						get("/devices/findByIdentifier/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void shouldReturnListOfDevices() throws Exception {
		DeviceRecord device1 = new DeviceRecord("device1", "Device One");
		deviceService.createDevice(device1);
		DeviceRecord device2 = new DeviceRecord("device2", "Device Two");
		deviceService.createDevice(device2);
		
		mockMvc.perform(get("/devices/findAll").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(device1.name()))
				.andExpect(jsonPath("$[0].brand").value(device1.brand()))
				.andExpect(jsonPath("$[1].name").value(device2.name()))
				.andExpect(jsonPath("$[1].brand").value(device2.brand()));
	}
	
	@Test
	void shouldUpdateDevice() throws Exception {
		String identifier = "device1";
		DeviceRecord deviceToUpdate = new DeviceRecord("device1", "Updated Device");
		deviceService.createDevice(deviceToUpdate);
		
		mockMvc.perform(put("/devices/updateFull/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON)
						.content(
								"{\"identifier\":\"device1\", \"name\":\"Updated Device\", \"brand\":\"Updated " +
										"Brand\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Updated Device"))
				.andExpect(jsonPath("$.brand").value("Updated Brand"));
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceDoesNotExist() throws Exception {
		String identifier = "device1";
		
		mockMvc.perform(put("/devices/updateFull/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON)
						.content(
								"{\"identifier\":\"device1\", \"name\":\"Updated Device\", \"brand\":\"Updated " +
										"Brand\"}"))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void shouldPartiallyUpdateDevice() throws Exception {
		String identifier = "device1";
		DeviceRecord deviceToUpdate = new DeviceRecord("device1", "Updated Device");
		deviceService.createDevice(deviceToUpdate);
		
		mockMvc.perform(
						patch("/devices/partialUpdate/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON)
								.content("{\"identifier\":\"device1\", \"name\":\"Updated Device\", " +
										"\"brand\":\"Updated " +
										"Brand\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Updated Device"))
				.andExpect(jsonPath("$.brand").value("Updated Brand"));
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceDoesNotExistForPartialUpdate() throws Exception {
		String identifier = "device1";
		
		mockMvc.perform(
				patch("/devices/partialUpdate/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON)
						.content("{\"identifier\":\"device1\", \"name\":\"Updated Device\", \"brand\":\"Updated " +
								"Brand\"}")).andExpect(status().isNotFound());
	}
	
	@Test
	void shouldReturnNoContentWhenDeviceDeleted() throws Exception {
		String identifier = "device1";
		DeviceRecord deviceRecord = new DeviceRecord("device1", "Updated Device");
		deviceService.createDevice(deviceRecord);
		
		mockMvc.perform(delete("/devices/delete/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceNotExist() throws Exception {
		String identifier = "device1";
		
		mockMvc.perform(delete("/devices/delete/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void shouldReturnDeviceWhenFoundByBrand() throws Exception {
		String brand = "BrandName";
		DeviceRecord mockDevice = new DeviceRecord("DeviceName", "BrandName");
		deviceService.createDevice(mockDevice);
		
		mockMvc.perform(get("/devices/findByBrand/{brand}", brand).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"name\":\"DeviceName\",\"brand\":\"BrandName\"}"));
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceNotFoundByBrand() throws Exception {
		String brand = "BrandName";
		mockMvc.perform(get("/devices/findByBrand/{brand}", brand).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	
}