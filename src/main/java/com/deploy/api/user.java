package com.deploy.api;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "USERS")
public class user {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String role;
	
	@NotBlank(message = "cant be blank")
    private String name;
	
	@Column(unique = true)
	@Email
	private String email;
	@NotBlank(message ="require password")
	private String password;
	private String about;
	
	//@AssertTrue
	private boolean enabled;
	
	@Column(length = 500)
	private String imageurl;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval=true)
	private List<contact>contacts=new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public List<contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<contact> contacts) {
		this.contacts = contacts;
	}


	public user() {
		super();
		// TODO Auto-generated constructor stub
	}

    
	
	
}
