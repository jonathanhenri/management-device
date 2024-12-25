package com.global.device;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;

public abstract class BaseIntegrationTest {
	
	private static GenericContainer<?> redisContainer;
	
	@BeforeAll
	public static void setup() {
		redisContainer = new GenericContainer<>("redis:latest").withExposedPorts(6379);
		
		redisContainer.start();
		System.setProperty("spring.redis.port", redisContainer.getFirstMappedPort().toString());
	}
	
	@AfterAll
	public static void tearDown() {
		if (redisContainer != null) {
			redisContainer.stop();
		}
	}
	
}
