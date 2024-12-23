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
//
//	// 2. Get device by identifier
//	@GetMapping("/{identifier}")
//	public ResponseEntity<Device> getDeviceByIdentifier(@PathVariable String identifier) {
//		Device device = deviceProvider.getDeviceByIdentifier(identifier);
//		if (device != null) {
//			return new ResponseEntity<>(device, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
//
//	// 3. List all devices
//	@GetMapping
//	public ResponseEntity<List<Device>> listAllDevices() {
//		List<Device> devices = deviceProvider.listAllDevices();
//		return new ResponseEntity<>(devices, HttpStatus.OK);
//	}
//
//	// 4. Update device (full update)
//	@PutMapping("/{identifier}")
//	public ResponseEntity<Device> updateDevice(@PathVariable String identifier, @RequestBody Device device) {
//		Device updatedDevice = deviceProvider.updateDevice(identifier, device);
//		if (updatedDevice != null) {
//			return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
//
//	// 4. Partial update device
//	@PatchMapping("/{identifier}")
//	public ResponseEntity<Device> partialUpdateDevice(@PathVariable String identifier, @RequestBody Device device) {
//		Device updatedDevice = deviceProvider.partialUpdateDevice(identifier, device);
//		if (updatedDevice != null) {
//			return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
//
//	// 5. Delete a device
//	@DeleteMapping("/{identifier}")
//	public ResponseEntity<Void> deleteDevice(@PathVariable String identifier) {
//		boolean isDeleted = deviceProvider.deleteDevice(identifier);
//		if (isDeleted) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
//
//	// 6. Search device by brand
//	@GetMapping("/search")
//	public ResponseEntity<List<Device>> searchDeviceByBrand(@RequestParam String brand) {
//		List<Device> devices = deviceProvider.searchDeviceByBrand(brand);
//		return new ResponseEntity<>(devices, HttpStatus.OK);
//	}
}
