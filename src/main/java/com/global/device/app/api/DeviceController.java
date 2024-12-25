package com.global.device.app.api;

import com.global.device.app.model.DeviceHateoas;
import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceLinkBuilderService;
import com.global.device.app.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
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
@Tag(name = "Device Controller", description = "Management of a device database.")
public class DeviceController {
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private DeviceLinkBuilderService deviceLinkBuilderService;
	@PostMapping("/create")
	@Operation(summary = "Create new device")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Device created")})
	public ResponseEntity<DeviceHateoas> addDevice(@RequestBody DeviceRecord deviceRecord) {
		DeviceHateoas createdDevice = deviceService.createDevice(deviceRecord);
		
		deviceLinkBuilderService.addLinks(createdDevice);
		
		return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
	}
	
	@GetMapping("/findByIdentifier/{identifier}")
	@Operation(summary = "Find device by identifier")
	public ResponseEntity<DeviceHateoas> getDeviceByIdentifier(@PathVariable String identifier) {
		DeviceHateoas device = deviceService.getDeviceByIdentifier(identifier);
		
		deviceLinkBuilderService.addLinks(device);
		
		return new ResponseEntity<>(device, HttpStatus.OK);
	}
	
	@GetMapping("/findAll")
	@Operation(summary = "Find all devices")
	public ResponseEntity<List<DeviceRecord>> listAllDevices() {
		List<DeviceRecord> devices = deviceService.listAllDevices();
		return new ResponseEntity<>(devices, HttpStatus.OK);
	}
	
	@PutMapping("/updateFull/{identifier}")
	public ResponseEntity<DeviceHateoas> updateDevice(@PathVariable String identifier,
													 @RequestBody DeviceRecord device) {
		DeviceHateoas updatedDevice = deviceService.updateAllDevice(identifier, device);
		
		deviceLinkBuilderService.addLinks(updatedDevice);
		if (updatedDevice != null) {
			return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping("/partialUpdate/{identifier}")
	public ResponseEntity<DeviceHateoas> partialUpdateDevice(@PathVariable String identifier,
															@RequestBody DeviceRecord device) {
		DeviceHateoas updatedDevice = deviceService.updatePartialDevice(identifier, device);
		
		deviceLinkBuilderService.addLinks(updatedDevice);
		if (updatedDevice != null) {
			return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{identifier}")
	public ResponseEntity<Void> deleteDevice(@PathVariable String identifier) {
		boolean isDeleted = deviceService.deleteDevice(identifier);
		if (isDeleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/findByBrand/{brand}")
	@Operation(summary = "Find devices by brand")
	public ResponseEntity<List<DeviceRecord>> getDeviceByBrand(@PathVariable String brand) {
		List<DeviceRecord> devices = deviceService.getDeviceByBrand(brand);
		
		if (CollectionUtils.isNotEmpty(devices)) {
			return new ResponseEntity<>(devices, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
