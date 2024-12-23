package com.global.device.infra.mapper;


import com.global.device.app.model.DeviceRecord;
import com.global.device.domain.entity.Device;
import com.global.device.infra.model.DeviceData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceDataMapper {

	DeviceData toEntity(Device device);
	
	Device toData(DeviceData deviceData);
	
}
