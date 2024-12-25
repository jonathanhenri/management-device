package com.global.device.app.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
public class DeviceHateoas extends RepresentationModel<DeviceHateoas> {
	
	private String name;
	private String brand;
	private String identifier;
	
}
