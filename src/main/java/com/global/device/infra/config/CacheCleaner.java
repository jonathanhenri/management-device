package com.global.device.infra.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class CacheCleaner {
	
	
	private final RedisTemplate<String, Object> redisTemplate;
	
	public CacheCleaner(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	public void clearCache() {
		redisTemplate.getConnectionFactory().getConnection().flushAll();
		System.out.println("Todos os caches foram limpos no Redis.");
	}
	
}
