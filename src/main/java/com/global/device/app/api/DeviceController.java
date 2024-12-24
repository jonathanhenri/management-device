package com.global.device.app.api;

import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
//
//	// 2. Get device by identifier
	@GetMapping("/{identifier}")
	@Operation(summary = "Find device by identifier")
	public ResponseEntity<DeviceRecord> getDeviceByIdentifier(@PathVariable String identifier) {
		DeviceRecord device = deviceService.getDeviceByIdentifier(identifier);
		
		if (device != null) {
			return new ResponseEntity<>(device, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
// 3. List all devices
	@GetMapping
	@Operation(summary = "Find all devices")
	public ResponseEntity<List<DeviceRecord>> listAllDevices() {
		List<DeviceRecord> devices = deviceService.listAllDevices();
		return new ResponseEntity<>(devices, HttpStatus.OK);
	}
	
	// 4. Update device (full update)
	@PutMapping("/{identifier}")
	public ResponseEntity<DeviceRecord> updateDevice(@PathVariable String identifier, @RequestBody DeviceRecord device) {
		DeviceRecord updatedDevice = deviceService.updateDevice(identifier, device);
		if (updatedDevice != null) {
			return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// 4. Partial update device
	@PatchMapping("/{identifier}")
	public ResponseEntity<DeviceRecord> partialUpdateDevice(@PathVariable String identifier, @RequestBody DeviceRecord device) {
		DeviceRecord updatedDevice = deviceService.updateDevice(identifier, device);
		if (updatedDevice != null) {
			return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// 5. Delete a device
	@DeleteMapping("/{identifier}")
	public ResponseEntity<Void> deleteDevice(@PathVariable String identifier) {
		boolean isDeleted = deviceService.deleteDevice(identifier);
		if (isDeleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
