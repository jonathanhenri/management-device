package com.global.device.infra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(name = "device")
public class DeviceData {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String identifier;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String model;
	
	@Column(nullable = false)
	private String brand;
	
	@Column(nullable = false)
	private LocalDateTime createTime;
}
