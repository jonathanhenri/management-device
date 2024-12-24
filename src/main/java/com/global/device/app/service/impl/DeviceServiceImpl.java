package com.global.device.app.service.impl;

import com.global.device.app.mapper.DeviceMapper;
import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceService;
import com.global.device.domain.useCase.DeviceUseCase;
import lombok.RequiredArgsConstructor;
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
	public DeviceRecord createDevice(DeviceRecord deviceRecord) {
		return deviceMapper.toEntity(deviceUseCase.createDevice(deviceMapper.toRecord(deviceRecord)));
	}
	
	@Override
	public DeviceRecord getDeviceByIdentifier(String identifier) {
		return deviceMapper.toEntity(deviceUseCase.getDeviceByIdentifier(identifier));
	}
	
	@Override
	public List<DeviceRecord> listAllDevices() {
		return deviceUseCase.listAllDevices().stream().map(deviceMapper::toEntity).collect(Collectors.toList());
	}
	
}
