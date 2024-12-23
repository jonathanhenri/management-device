package com.global.device.app.service.impl;

import com.global.device.app.mapper.DeviceMapper;
import com.global.device.app.model.DeviceRecord;
import com.global.device.app.service.DeviceService;
import com.global.device.domain.useCase.DeviceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
	
	private DeviceUseCase deviceUseCase;
	private DeviceMapper deviceMapper;
	
	@Override
	public void createDevice(DeviceRecord deviceRecord) {
		deviceUseCase.createDevice(deviceMapper.toRecord(deviceRecord));
	}
	
}
