package com.global.device.domain.useCase.impl;

import com.global.device.domain.DeviceProvider;
import com.global.device.domain.entity.Device;
import com.global.device.domain.useCase.DeviceUseCase;

//todo criar as anotacoes
public class DeviceUseCaseImpl implements DeviceUseCase {
	
	private DeviceProvider deviceProvider;
	@Override
	public Device createDevice(Device device) {
		
		return deviceProvider.createDevice(device);
	}
	
}