package com.contactura.contactura.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactura.contactura.model.ContacturaUser;
import com.contactura.contactura.repository.ContacturaUserRepository;

@Service
public class ContacturaUserService {

	@Autowired
	private ContacturaUserRepository repository;

	public Optional<ContacturaUser> findById(Long id) {
		return this.repository.findById(id);
	}

	public String deleteById(Long id) throws Exception {
		this.findById(id).orElseThrow(() -> new NullPointerException("Usuário não existente"));
		this.repository.deleteById(id);
		Optional<ContacturaUser> user = this.findById(id);
		if (user.isPresent()) {
			throw new Exception("Não foi possivel deletar o usuário");
		}
		return "Usuário deletado com sucesso";
	}

}
