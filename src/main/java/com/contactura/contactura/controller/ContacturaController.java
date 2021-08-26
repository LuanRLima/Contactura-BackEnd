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

import com.contactura.contactura.dom.TwoTables;
import com.contactura.contactura.model.Contactura;
import com.contactura.contactura.repository.ContacturaRepository;
import com.contactura.contactura.service.ContacturaService;
import com.contactura.contactura.service.QueryService;

@RestController
@RequestMapping({ "/contactura" }) // http://localhost:8090/contactura
public class ContacturaController {

	@Autowired
	private ContacturaRepository repository;
	@Autowired
	private ContacturaService service;
	@Autowired
    QueryService queryservice;

	// List ALL - //http://localhost:8090/contactura
	@GetMapping
	public List<?> findAll() {
		return repository.findAll();
		
	}

	// Find By Ud - //http://localhost:8090/contactura/{id}
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// Create - http://localhost:8095/contactura
	@PostMapping
	public Contactura create(@RequestBody Contactura contactura) {
		return repository.save(contactura);
	}

	// Update - http://localhost/contactura/{id}
	@PutMapping(value = "{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody Contactura contactura) {
		return repository.findById(id).map(record -> {
			record.setName(contactura.getName());
			record.setEmail(contactura.getEmail());
			record.setPhone(contactura.getPhone());
			Contactura update = repository.save(record);
			return ResponseEntity.ok().body(update);
		}).orElse(ResponseEntity.notFound().build());
	}

	// Delete - http://localhost:8095/conctura/{id}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable long id) {
		try {
			return new ResponseEntity<String>(this.service.deleteById(id), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.LOCKED);
		}
	}
	
	@GetMapping("/joinQuery")
    public List<TwoTables> getQuery()
    {
        return queryservice.JPQLQuery();
    }
}
