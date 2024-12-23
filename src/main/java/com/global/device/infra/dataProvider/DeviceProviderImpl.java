package com.global.device.infra.dataProvider;

import com.global.device.domain.DeviceProvider;
import com.global.device.domain.entity.Device;
import com.global.device.infra.mapper.DeviceDataMapper;
import com.global.device.infra.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@@RequiredArgsConstructor
public class DeviceProviderImpl implements DeviceProvider {
	
	private final DeviceRepository deviceRepository;
	private final DeviceDataMapper deviceDataMapper;
	
	@Override
	public void createDevice(Device device) {
		deviceRepository.save(deviceDataMapper.toEntity(device));
	}
	
}
