package com.datumsolutions.rest;

import javax.ws.rs.core.MediaType;

import com.datumsolutions.entities.Student;
import com.datumsolutions.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/student")
@RestController
public class StudentResource {

	@Autowired
	private StudentService studentService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Student>> getUsers() {
    	final List<Student> students = studentService.findByPattern("*");
		return new ResponseEntity<>(students, HttpStatus.OK);
	}


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Student> getUsers(@PathVariable("id") final String userId) {
    	final Student student = studentService.findById(userId);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> createUser(@RequestBody final Student student) {
    	studentService.save(student);
    	return new ResponseEntity<>(HttpStatus.CREATED);
	}

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> updateUser(@PathVariable("id") final String userId, @RequestBody final Student student) {
    	student.setId(userId);
    	studentService.update(student);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final String userId) {
    	studentService.delete(userId);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
