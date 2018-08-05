package net.honeyflower.lectures.springcache.repository;

import org.springframework.data.repository.CrudRepository;

import net.honeyflower.lectures.springcache.model.Users;

public interface UsersRepository extends CrudRepository<Users, Long>{

	Users findByName(String name);
}
