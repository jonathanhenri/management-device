package com.global.device.infra.dataProvider;

import com.global.device.domain.DeviceProvider;
import com.global.device.domain.entity.Device;
import com.global.device.infra.exceptions.EntityNotFound;
import com.global.device.infra.mapper.DeviceDataMapper;
import com.global.device.infra.model.DeviceData;
import com.global.device.infra.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.Optional;

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
	
	@Override
	@Cacheable(value = "deviceCache", key = "#identifier")
	public Device getDeviceByIdentifier(String identifier) {
		DeviceData deviceData = deviceRepository.findByName(identifier)
				.orElseThrow(() -> new EntityNotFound("Device not found :".concat(identifier)));
		return deviceDataMapper.toData(deviceData);
	}
	
}
