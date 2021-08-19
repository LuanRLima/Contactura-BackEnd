package com.contactura.contactura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping({ "/contactura" }) // http:localhost:8090/contacturaUser
public class ContacturaUserController {

	@Autowired
	private ContacturaUserRepository userRepository;

	// List ALL - //http://localhost:8095/contactura
	@GetMapping
	public List findAll() {
		return userRepository.findAll();
	}

	// Find By Id - //http:localhost:8090/contacturaUser/{id}
	@GetMapping(value = "{id}")
	public ResponseEntity findById(@PathVariable long id) {
		return userRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());

	}

	// Create - http://localhost:8095/contacturaUser
	@PostMapping
	public ContacturaUser create(@RequestBody ContacturaUser contacturaUser) {
		return userRepository.save(contacturaUser);
	}

	// Update - http://localhost/contacturaUser/{id}
	@PutMapping(value = "{id}")
	public ResponseEntity upadte(@PathVariable long id, @RequestBody ContacturaUser contacturaUser) {
		return userRepository.findById(id).map(record -> {
			record.setName(contacturaUser.getName());
			record.setPassoword(contacturaUser.getPassoword());
			record.setUsername(contacturaUser.getUsername());
			record.setAdmin(contacturaUser.getAdmin());
			ContacturaUser update = userRepository.save(record);
			return ResponseEntity.ok().body(update);
		}).orElse(ResponseEntity.notFound().build());

	}

	// Delete - http://localhost/contacturaUser/{id}
	@DeleteMapping(path = { "/{id" })
	public ResponseEntity delete(@PathVariable long id) {
		return userRepository.findById(id).map(record -> {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());

	}

}
