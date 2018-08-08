package net.honeyflower.lectures.springjpatest.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.honeyflower.lectures.springjpatest.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	List<Customer> findByLastName(String lastName);
}
