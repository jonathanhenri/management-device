package com.global.device.infra.dataProvider;

import com.global.device.domain.DeviceProvider;
import com.global.device.domain.entity.Device;
import com.global.device.infra.mapper.DeviceDataMapper;
import com.global.device.infra.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceProviderImpl implements DeviceProvider {
	
	private DeviceRepository deviceRepository;
	private DeviceDataMapper deviceDataMapper;
	
	@Override
	@Transactional
	public Device createDevice(Device device) {
		return deviceDataMapper.toData(deviceRepository.save(deviceDataMapper.toEntity(device)));
	}
	
}
