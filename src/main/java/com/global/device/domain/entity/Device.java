package com.global.device.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Device {
	
	private String name;
	private String brand;
	private LocalDateTime createTime;
}
