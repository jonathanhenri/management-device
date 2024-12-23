package com.global.device.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Device {
	
	private String identifier;
	private String name;
	private String model;
	private String brand;
}
