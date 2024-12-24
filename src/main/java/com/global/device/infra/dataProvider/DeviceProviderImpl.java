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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class DeviceProviderImpl implements DeviceProvider {
	
	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private DeviceDataMapper deviceDataMapper;
	
	@Override
	@Transactional
	@CacheEvict(value = "allDevicesCache", allEntries = true)
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
	
	@Override
	@Cacheable(value = "allDevicesCache")
	public List<Device> listAllDevices() {
		return deviceRepository.findAll().stream().map(deviceDataMapper::toData).collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	@CacheEvict(value = {"deviceCache", "allDevicesCache"}, key = "#identifier")
	public Device updateDevice(String identifier, Device device) {
		return deviceRepository.findByName(identifier)
				.map(existingDevice -> {
					updateFieldIfNotNull(existingDevice::setName, device.getName());
					updateFieldIfNotNull(existingDevice::setBrand, device.getBrand());
					updateFieldIfNotNull(existingDevice::setCreateTime, device.getCreateTime());
					return deviceRepository.save(existingDevice);
				})
				.map(deviceDataMapper::toData)
				.orElseThrow(() -> new EntityNotFound("Device not found with identifier: " + identifier));
	}
	
	@Override
	public boolean deleteDevice(String identifier) {
		return deviceRepository.deleteByName(identifier) > 0;
	}
	
	private <T> void updateFieldIfNotNull(Consumer<T> setter, T value) {
		if (value != null) {
			setter.accept(value);
		}
	}
	
}
