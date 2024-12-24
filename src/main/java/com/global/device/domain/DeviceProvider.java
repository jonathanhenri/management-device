package com.global.device.domain;

import com.global.device.domain.entity.Device;
import java.util.List;

public interface DeviceProvider {
	
	Device createDevice(Device device);
	
	Device getDeviceByIdentifier(String identifier);
	
	List<Device> listAllDevices();
	
	Device updateDevice(String identifier, Device device);
	
	boolean deleteDevice(String identifier);
	
	List<Device> getDeviceByBrand(String brand);
}
