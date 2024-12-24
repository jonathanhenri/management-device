package com.global.device.domain.useCase.impl;

import com.global.device.domain.DeviceProvider;
import com.global.device.domain.entity.Device;
import com.global.device.domain.useCase.DeviceUseCase;
import com.global.device.infra.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class DeviceUseCaseImpl implements DeviceUseCase {
	
	private final DeviceProvider deviceProvider;
	@Override
	public Device createDevice(Device device) {
		device.setCreateTime(LocalDateTime.now());
		return deviceProvider.createDevice(device);
	}
	
	@Override
	public Device getDeviceByIdentifier(String identifier) {
		return deviceProvider.getDeviceByIdentifier(identifier);
	}
	
	@Override
	public List<Device> listAllDevices() {
		return deviceProvider.listAllDevices();
	}
	
	@Override
	public Device updateDevice(String identifier, Device device) {
		return deviceProvider.updateDevice(identifier, device);
	}
	
}
