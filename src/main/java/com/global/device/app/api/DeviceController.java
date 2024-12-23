package com.global.device.api;

import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceService;
import com.global.device.domain.DeviceProvider;
import com.global.device.domain.entity.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {
	
	private DeviceService deviceService;
	
	// 1. Add device
	@PostMapping
	public ResponseEntity<DeviceRecord> addDevice(@RequestBody DeviceRecord deviceRecord) {
		DeviceRecord createdDevice = deviceService.createDevice(deviceRecord);
		return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
	}
}
