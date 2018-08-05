package net.honeyflower.lectures.springcache.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import net.honeyflower.lectures.springcache.model.Users;
import net.honeyflower.lectures.springcache.repository.UsersRepository;

@Component
public class UsersCache {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Cacheable(value = "usersCache"/*, key = "#name"*/)
	public Users getUser(String name) {
		System.out.println("Retreving from database for name" + name);
		return usersRepository.findByName(name);
	}
}
