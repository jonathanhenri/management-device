package com.global.device.infra.dataProvider;

import com.global.device.domain.DeviceProvider;
import com.global.device.domain.entity.Device;
import com.global.device.infra.mapper.DeviceDataMapper;
import com.global.device.infra.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceProviderImpl implements DeviceProvider {
	
	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private DeviceDataMapper deviceDataMapper;
	
	@Override
	@Transactional
	public Device createDevice(Device device) {
		return deviceDataMapper.toData(deviceRepository.save(deviceDataMapper.toEntity(device)));
	}
	
}
