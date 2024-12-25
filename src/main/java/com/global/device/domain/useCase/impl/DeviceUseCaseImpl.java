package com.global.device.domain.useCase.impl;

import com.global.device.domain.DeviceProvider;
import com.global.device.domain.entity.Device;
import com.global.device.domain.exception.DomainException;
import com.global.device.domain.useCase.DeviceUseCase;
import com.global.device.infra.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeviceUseCaseImpl implements DeviceUseCase {
	
	private final DeviceProvider deviceProvider;
	
	@Override
	public Device createDevice(Device device) {
		device.setCreateTime(LocalDateTime.now());
		
		if (Strings.isEmpty(device.getIdentifier())) {
			device.setIdentifier(UUID.randomUUID().toString());
		}
		
		if (device.isValid()) {
			return deviceProvider.createDevice(device);
		}
		
		throw new DomainException("Device not completed");
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
	public Device updateAllDevice(String identifier, Device device) {
		
		if (StringUtils.isEmpty(device.getIdentifier())) {
			device.setIdentifier(identifier);
		}
		
		if (!device.isValid()) {
			throw new DomainException("Device not completed");
		}
		
		return deviceProvider.updateDevice(identifier, device);
	}
	
	@Override
	public Device updatePartialDevice(String identifier, Device device) {
		return deviceProvider.updateDevice(identifier, device);
	}
	
	@Override
	public boolean deleteDevice(String identifier) {
		return deviceProvider.deleteDevice(identifier);
	}
	
	@Override
	public List<Device> getDeviceByBrand(String brand) {
		return deviceProvider.getDeviceByBrand(brand);
	}
	
}
