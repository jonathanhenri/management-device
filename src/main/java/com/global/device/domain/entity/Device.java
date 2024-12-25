package com.global.device.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device implements Serializable {
	
	private String name;
	private String brand;
	private LocalDateTime createTime;
	private String identifier;
	
	public boolean isValid() {
		return name != null && brand != null && identifier != null;
	}
	
}
