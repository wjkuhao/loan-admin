package com.mod.loan.config.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class RedisMapper {

	private static final String PRE_REDIS = "mod:admin:";

	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
	}
	@Autowired
	StringRedisTemplate redisTemplate;

	public long increment(String key, long delta, long exp) {
		Long current = redisTemplate.opsForValue().increment(PRE_REDIS+key, delta);
		redisTemplate.expire(PRE_REDIS+key, exp, TimeUnit.SECONDS);
		return current;
	}

	public void expire(String key, long exp) {
		redisTemplate.expire(PRE_REDIS+key, exp, TimeUnit.SECONDS);
	}

	public void remove(String key) {
		redisTemplate.delete(PRE_REDIS+key);
	}

	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(PRE_REDIS+key, serializer(value));
	}

	public void set(String key, Object value, long exp) {
		redisTemplate.opsForValue().set(PRE_REDIS+key, serializer(value), exp, TimeUnit.SECONDS);
	}

	public String get(String key) {
		return redisTemplate.opsForValue().get(PRE_REDIS+key);
	}

	public void lpush(String key, Object value) {
		redisTemplate.opsForList().leftPush(PRE_REDIS+key, serializer(value));
	}

	public String lpop(String key) {
		return redisTemplate.opsForList().leftPop(PRE_REDIS+key);
	}

	public <T> T lpop(String key, TypeReference<T> t) {
		return deserializer(redisTemplate.opsForList().leftPop(PRE_REDIS+key), t);
	}

	public <T> T get(String key, TypeReference<T> t) {
		return deserializer(redisTemplate.opsForValue().get(PRE_REDIS+key), t);
	}

	private String serializer(Object value) {
		try {
			if (value instanceof String) {
				return (String) value;
			}
			return mapper.writeValueAsString(value);
		} catch (Exception ex) {
			throw new RuntimeException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	private <T> T deserializer(String value, TypeReference<T> t) {
		try {
			return value == null ? null : mapper.readValue(value, t);
		} catch (Exception e) {
			throw new RuntimeException("Could not write JSON: " + e.getMessage(), e);
		}
	}

}
