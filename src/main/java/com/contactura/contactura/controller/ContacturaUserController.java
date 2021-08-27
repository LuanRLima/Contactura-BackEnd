package com.contactura.contactura.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contactura.contactura.model.ContacturaUser;
import com.contactura.contactura.repository.ContacturaUserRepository;

@RestController
@RequestMapping({ "/user" }) // http:localhost:8090/user
public class ContacturaUserController {

	@Autowired
	private ContacturaUserRepository userRepository;
	
	@RequestMapping("/login")
	@GetMapping
	public String login(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring("Basic".length()).trim();
		return token;
	}

	// List ALL - //http://localhost:8095/contactura
	@GetMapping
	public List<?> findAll() {
		return userRepository.findAll();
	}

	// Find By Id - //http:localhost:8090/contacturaUser/{id}
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return userRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());

	}

	// Create - http://localhost:8095/contacturaUser
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ContacturaUser create(@RequestBody ContacturaUser contacturaUser) {
		contacturaUser.setPassword(encryptPassword(contacturaUser.getPassword()));
		return userRepository.save(contacturaUser);
	}

	// Update - http://localhost/contacturaUser/{id}
	@PutMapping(value = "{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> upadte(@PathVariable long id, @RequestBody ContacturaUser contacturaUser) {
		contacturaUser.setPassword(encryptPassword(contacturaUser.getPassword()));
		return userRepository.findById(id).map(record -> {
			record.setName(contacturaUser.getName());
			record.setPassword(contacturaUser.getPassword());
			record.setUsername(contacturaUser.getUsername());
			record.setAdmin(contacturaUser.isAdmin());
			ContacturaUser update = userRepository.save(record);
			return ResponseEntity.ok().body(update);
		}).orElse(ResponseEntity.notFound().build());

	}

	// Delete - http://localhost/contacturaUser/{id}
	@DeleteMapping(path = { "/{id" })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable long id) {
		ContacturaUser contacturaUser = new ContacturaUser();
		contacturaUser.setPassword(encryptPassword(contacturaUser.getPassword()));
		return userRepository.findById(id).map(record -> {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());

	}
	
	private String encryptPassword(String password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordCrypt = passwordEncoder.encode(password);
		
		return passwordCrypt;
	}
	

}
