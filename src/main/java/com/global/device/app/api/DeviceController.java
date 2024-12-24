package com.global.device.app.api;

import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
@Tag(name = "Device Controller", description = "Operações relacionadas aos dispositivos")
public class DeviceController {
	
	@Autowired
	private DeviceService deviceService;
	
	// 1. Add device
	@PostMapping
	@Operation(summary = "Create new device")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Device created")
	})
	public ResponseEntity<DeviceRecord> addDevice(@RequestBody DeviceRecord deviceRecord) {
		DeviceRecord createdDevice = deviceService.createDevice(deviceRecord);
		return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
	}
}
