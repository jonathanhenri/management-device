package com.global.device.domain;

import com.global.device.domain.entity.Device;

public interface DeviceProvider {
	
	Device createDevice(Device device);
}
