package com.deploy.api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "`order`")
public class order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String orderId;
	private String amount;
	private String receipt;
	private String status;
	
	@ManyToOne
	private user User;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public user getUser() {
		return User;
	}

	public void setUser(user user) {
		User = user;
	}

	public order() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
