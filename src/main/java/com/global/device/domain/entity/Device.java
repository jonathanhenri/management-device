package com.global.device.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device implements Serializable {
	
	private String name;
	private String brand;
	private LocalDateTime createTime;
	
	public boolean isValid() {
		return name != null && brand != null && createTime != null;
	}

}
