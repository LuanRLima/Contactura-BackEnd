package com.contactura.contactura.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactura.contactura.dom.TwoTables;
import com.contactura.contactura.repository.ContacturaRepository;
import com.contactura.contactura.repository.ContacturaUserRepository;

@Service
public class QueryService implements IQueryService {
	
	@Autowired
	ContacturaRepository contactRepositrory;
	
	@Autowired
	ContacturaUserRepository contactUserRepositrory;
	
	@Autowired
	EntityManagerFactory emf;
	
	@Override
	public List<TwoTables> JPQLQuery(){
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createNamedQuery("SELECT contact FROM Contactura contact join ContacturaUser" );
		@SuppressWarnings("unchecked")
        List<TwoTables> list =(List<TwoTables>)query.getResultList();
        System.out.println("Student Name :");
        em.close();
        
        return list;
		
	}
}
