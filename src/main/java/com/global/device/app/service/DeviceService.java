package com.global.device.app.service;

import com.global.device.app.model.DeviceHateoas;
import com.global.device.app.model.DeviceRecord;
import java.util.List;

public interface DeviceService {
	
	DeviceHateoas createDevice(DeviceRecord deviceRecord);
	
	DeviceHateoas getDeviceByIdentifier(String identifier);
	
	List<DeviceRecord> listAllDevices();
	
	DeviceHateoas updateAllDevice(String identifier, DeviceRecord device);
	
	DeviceHateoas updatePartialDevice(String identifier, DeviceRecord device);
	
	boolean deleteDevice(String identifier);
	
	List<DeviceRecord> getDeviceByBrand(String brand);
	
}
