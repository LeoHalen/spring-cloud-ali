package com.pocketcloud.auth.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Zg.Li · 2020/1/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicaiton {

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void redisTest() {
		// redis存储数据
		String key = "name";
		redisTemplate.opsForValue().set(key, "yukong");
		// 获取数据
		String value = (String) redisTemplate.opsForValue().get(key);
		System.out.println("获取缓存中key为" + key + "的值为：" + value);

		String userKey = "test_key";
		redisTemplate.opsForValue().set(userKey, "test01");
		String result = (String) redisTemplate.opsForValue().get(userKey);
		System.out.println("获取缓存中key为" + userKey + "的值为：" + result);

	}
}
