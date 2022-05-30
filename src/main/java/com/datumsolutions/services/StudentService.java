package com.datumsolutions.services;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.NotFoundException;

import com.datumsolutions.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	@Autowired
    private RedisTemplate<String, Student> userTemplate;

	private static final String REDIS_PREFIX_USERS = "users";

	private static final String REDIS_KEYS_SEPARATOR = ":";

	public List<Student> findByPattern(final String pattern) {
		return getValueOperations().multiGet(userTemplate.keys(getRedisKey(pattern)));
	}

	public Student findById(final String userId) {
		final Student student = getValueOperations().get(getRedisKey(UUID.fromString(userId).toString()));
		if(student == null) {
			throw new NotFoundException("User does not exist in the DB");
		}
		return student;
	}

	public void save(final Student student) {
		student.setId(UUID.randomUUID().toString());
		getValueOperations().set(getRedisKey(student.getId()), student);
	}

	public void update(final Student student) {
		findById(student.getId());
		getValueOperations().set(getRedisKey(student.getId()), student);
	}

	public void delete(final String userId) {
		if(!userTemplate.delete(getRedisKey(UUID.fromString(userId).toString()))) {
			throw new NotFoundException("User does not exist in the DB");
		}
	}

	private String getRedisKey(final String userId) {
        return REDIS_PREFIX_USERS + REDIS_KEYS_SEPARATOR + userId;
    }

	private ValueOperations<String, Student> getValueOperations() {
		return userTemplate.opsForValue();
	}

}
