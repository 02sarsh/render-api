package com.deploy.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<user, Integer>{
	
	@Query("select u from user u where u.email = :email")
	public user getuserbyemail(@Param("email")String email);
	
	@Query("select password from user u where u.email = :email")
	public String getpassbyemail(@Param("email")String email);
	
	
	

}
