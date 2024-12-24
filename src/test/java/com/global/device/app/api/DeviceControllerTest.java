package com.global.device.app.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceService;
import com.global.device.infra.exceptions.EntityNotFound;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DeviceService deviceService;
	
	@Autowired
	private Gson gson;
	
	@Test
	void shouldCreateDeviceSuccessfully() throws Exception {
		DeviceRecord deviceRequest = new DeviceRecord("DeviceName", "BrandName");
		DeviceRecord deviceResponse = new DeviceRecord("DeviceName", "BrandName");
		when(deviceService.createDevice(any(DeviceRecord.class))).thenReturn(deviceResponse);
		
		mockMvc.perform(
						post("/devices/create").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(deviceRequest)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("DeviceName"))
				.andExpect(jsonPath("$.brand").value("BrandName"));
	}
	
	@Test
	void shouldReturnDeviceWhenFound() throws Exception {
		String identifier = "device123";
		DeviceRecord deviceRecord = new DeviceRecord("device123", "Device Brand");
		
		when(deviceService.getDeviceByIdentifier(identifier)).thenReturn(deviceRecord);
		
		mockMvc.perform(
						get("/devices/findByIdentifier/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value(deviceRecord.name()))
				.andExpect(jsonPath("$.brand").value(deviceRecord.brand()));
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceNotFound() throws Exception {
		String identifier = "device123";
		
		when(deviceService.getDeviceByIdentifier(any())).thenReturn(null);
		
		mockMvc.perform(
						get("/devices/findByIdentifier/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void shouldReturnListOfDevices() throws Exception {
		DeviceRecord device1 = new DeviceRecord("device1", "Device One");
		DeviceRecord device2 = new DeviceRecord("device2", "Device Two");
		
		when(deviceService.listAllDevices()).thenReturn(List.of(device1, device2));
		
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
		
		when(deviceService.updateDevice(any(), any())).thenReturn(deviceToUpdate);
		
		mockMvc.perform(put("/devices/updateFull/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON)
						.content(
								"{\"identifier\":\"device1\", \"name\":\"Updated Device\", \"brand\":\"Updated " +
										"Brand\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value(deviceToUpdate.name()))
				.andExpect(jsonPath("$.brand").value(deviceToUpdate.brand()));
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceDoesNotExist() throws Exception {
		String identifier = "device1";
		DeviceRecord deviceToUpdate = new DeviceRecord("device1", "Updated Device");
		
		when(deviceService.updateDevice(identifier, deviceToUpdate)).thenReturn(null);
		
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
		
		when(deviceService.updateDevice(any(), any())).thenReturn(deviceToUpdate);
		
		mockMvc.perform(
						patch("/devices/partialUpdate/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON)
								.content("{\"identifier\":\"device1\", \"name\":\"Updated Device\", " +
										"\"brand\":\"Updated " +
										"Brand\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(deviceToUpdate.name()))
				.andExpect(jsonPath("$.brand").value(deviceToUpdate.brand()));
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceDoesNotExistForPartialUpdate() throws Exception {
		String identifier = "device1";
		
		doThrow(new EntityNotFound("Entity not found")).when(deviceService).updateDevice(any(), any());
		
		mockMvc.perform(
				patch("/devices/partialUpdate/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON)
						.content("{\"identifier\":\"device1\", \"name\":\"Updated Device\", \"brand\":\"Updated " +
								"Brand\"}")).andExpect(status().isNotFound());
	}
	
	@Test
	void shouldReturnNoContentWhenDeviceDeleted() throws Exception {
		String identifier = "device1";
		
		doReturn(true).when(deviceService).deleteDevice(identifier);
		
		mockMvc.perform(delete("/devices/delete/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceNotExist() throws Exception {
		String identifier = "device1";
		
		doThrow(new EntityNotFound("Entity not found")).when(deviceService).deleteDevice(any());
		
		mockMvc.perform(delete("/devices/delete/{identifier}", identifier).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void shouldReturnDeviceWhenFoundByBrand() throws Exception {
		String brand = "BrandName";
		DeviceRecord mockDevice = new DeviceRecord("DeviceName", "BrandName");
		
		doReturn(mockDevice).when(deviceService).getDeviceByBrand(brand);
		
		mockMvc.perform(get("/devices/findByBrand/{brand}", brand).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"name\":\"DeviceName\",\"brand\":\"BrandName\"}"));
	}
	
	@Test
	void shouldReturnNotFoundWhenDeviceNotFoundByBrand() throws Exception {
		String brand = "BrandName";
		
		doReturn(null).when(deviceService).getDeviceByBrand(brand);
		
		mockMvc.perform(get("/devices/findByBrand/{brand}", brand).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	
}