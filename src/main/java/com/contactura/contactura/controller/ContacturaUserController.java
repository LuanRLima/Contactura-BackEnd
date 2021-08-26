package com.contactura.contactura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.contactura.contactura.model.ContacturaUser;
import com.contactura.contactura.repository.ContacturaUserRepository;
import com.contactura.contactura.service.ContacturaUserService;

@RestController
@RequestMapping({ "/user" }) // http:localhost:8090/user
public class ContacturaUserController {

	@Autowired
	private ContacturaUserRepository userRepository;
	// List ALL - //http:localhost:8090/contacturaUser
	@Autowired
	private ContacturaUserService service;

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
			record.setPassword(contacturaUser.getPassword());
			record.setUsername(contacturaUser.getUsername());
			record.setAdmin(contacturaUser.isAdmin());
			ContacturaUser update = userRepository.save(record);
			return ResponseEntity.ok().body(update);
		}).orElse(ResponseEntity.notFound().build());

	}

	// Delete - http://localhost/contacturaUser/{id}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable long id) {
		try {
			return new ResponseEntity<String>(this.service.deleteById(id), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("Error" + e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error" + e.getMessage(), HttpStatus.LOCKED);
		}
	}

}
