package com.global.device.app.model;

public record DeviceRecord(String name, String brand, String identifier) {
	
	public DeviceRecord(String name, String brand) {
		this(name, brand, null);
	}
	
}
