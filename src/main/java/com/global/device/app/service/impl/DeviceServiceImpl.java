package com.global.device.app.service.impl;

import com.global.device.app.mapper.DeviceMapper;
import com.global.device.app.model.DeviceHateoas;
import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceService;
import com.global.device.domain.entity.Device;
import com.global.device.domain.useCase.DeviceUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {
	
	@Autowired
	private DeviceUseCase deviceUseCase;
	@Autowired
	private DeviceMapper deviceMapper;
	
	@Override
	public DeviceHateoas createDevice(DeviceRecord deviceRecord) {
		Device device = deviceUseCase.createDevice(deviceMapper.toRecord(deviceRecord));
		return deviceMapper.toHateoas(device);
	}
	
	@Override
	public DeviceHateoas getDeviceByIdentifier(String identifier) {
		return deviceMapper.toHateoas(deviceUseCase.getDeviceByIdentifier(identifier));
	}
	
	@Override
	public List<DeviceRecord> listAllDevices() {
		return deviceUseCase.listAllDevices().stream().map(deviceMapper::toEntity).collect(Collectors.toList());
	}
	
	@Override
	public DeviceHateoas updateAllDevice(String identifier, DeviceRecord device) {
		return deviceMapper.toHateoas(deviceUseCase.updateAllDevice(identifier, deviceMapper.toRecord(device)));
	}
	
	@Override
	public DeviceHateoas updatePartialDevice(String identifier, DeviceRecord device) {
		return deviceMapper.toHateoas(deviceUseCase.updatePartialDevice(identifier, deviceMapper.toRecord(device)));
	}
	
	@Override
	public boolean deleteDevice(String identifier) {
		return deviceUseCase.deleteDevice(identifier);
	}
	
	@Override
	public List<DeviceRecord> getDeviceByBrand(String brand) {
		return deviceUseCase.getDeviceByBrand(brand).stream().map(deviceMapper::toEntity).collect(Collectors.toList());
	}
	
}
