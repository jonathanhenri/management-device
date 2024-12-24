package com.global.device.app.service;

import com.global.device.app.model.DeviceRecord;
import java.util.List;

public interface DeviceService {
	
	DeviceRecord createDevice(DeviceRecord deviceRecord);
	
	DeviceRecord getDeviceByIdentifier(String identifier);
	
	List<DeviceRecord> listAllDevices();
	
}
