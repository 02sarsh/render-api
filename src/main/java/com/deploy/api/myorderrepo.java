package com.deploy.api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface myorderrepo extends JpaRepository<order,Integer> {

	public order findByOrderId(String orderId);
}
