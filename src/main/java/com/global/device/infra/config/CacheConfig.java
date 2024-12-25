package com.global.device.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {
	
	@Value("${spring.redis.host:localhost}")
	private String hostName;
	
	@Value("${spring.redis.port:6379}")
	private int port;
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(hostName);
		jedisConnectionFactory.setPort(port);
		return jedisConnectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		return template;
	}
	
	@Bean
	public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.entryTtl(Duration.ofMinutes(5)).disableCachingNullValues();
		
		return RedisCacheManager.builder(redisTemplate.getConnectionFactory()).cacheDefaults(cacheConfig).build();
	}
	
}
