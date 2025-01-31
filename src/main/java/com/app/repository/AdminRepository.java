package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.modal.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {//Admin is a table entity class and long is type os primary key

	Optional<Admin> findByEmailAndPassword(String email, String password);


	Optional<Admin> findByEmail(String email);
	
	
	

	
}
