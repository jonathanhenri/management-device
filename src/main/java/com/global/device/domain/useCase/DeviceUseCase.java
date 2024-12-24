package com.global.device.domain.useCase;

import com.global.device.domain.entity.Device;
import java.util.List;

public interface DeviceUseCase {
	
	Device createDevice(Device device);
	
	Device getDeviceByIdentifier(String identifier);
	
	List<Device> listAllDevices();
	
	Device updateDevice(String identifier, Device device);
	
	boolean deleteDevice(String identifier);
	
	List<Device> getDeviceByBrand(String brand);
	
}
