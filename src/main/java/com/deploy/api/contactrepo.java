package com.deploy.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface contactrepo extends JpaRepository<contact,Integer>{

	@Query("from contact as c where c.User.id = :userId")
	public Page<contact>findallcontacts(@Param("userId")int userId,Pageable pageable);

	@Query(value = "delete from contact as c where c.cId = :cId")
      public contact deletecontact(@Param("cId")int cId);
	
	public List<contact>findByNameContaining(String name);

	
}
