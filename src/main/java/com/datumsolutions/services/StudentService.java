package com.datumsolutions.services;

import java.util.List;

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

	private static final String REDIS_PREFIX_STUDENTS = "students";

	private static final String REDIS_KEYS_SEPARATOR = ":";

	public List<Student> findByPattern(final String pattern) {
		return getValueOperations().multiGet(userTemplate.keys(getRedisKey(pattern)));
	}

	public Student findById(final String userId) {
		final Student student = getValueOperations().get((userId).toString());
		if(student == null) {
			throw new NotFoundException("User does not exist in the DB");
		}
		return student;
	}

	public void save(final Student student) {
		getValueOperations().set(getRedisKey(student.getId()), student);
	}

	public void update(final Student student) {
		findById(student.getId());
		getValueOperations().set(getRedisKey(student.getId()), student);
	}

	public void delete(final String userId) {
		if(!userTemplate.delete(getRedisKey((userId).toString()))) {
			throw new NotFoundException("User does not exist in the DB");
		}
	}

	private String getRedisKey(final String userId) {
        return REDIS_PREFIX_STUDENTS + REDIS_KEYS_SEPARATOR + userId;
    }

	private ValueOperations<String, Student> getValueOperations() {
		return userTemplate.opsForValue();
	}

}
