package com.global.device.app.mapper;


import com.global.device.app.model.DeviceRecord;
import com.global.device.domain.entity.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

	DeviceRecord toEntity(Device device);
	
	Device toRecord(DeviceRecord deviceRecord);
	
}
