package net.honeyflower.lectures.springcache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.honeyflower.lectures.springcache.cache.UsersCache;
import net.honeyflower.lectures.springcache.model.Users;

@RestController
@RequestMapping("rest/users")
public class UsersResource {
	
	@Autowired UsersCache usersCache;
	
	@RequestMapping("/{name}")
	public Users getUser(@PathVariable final String name) {
		return usersCache.getUser(name);
	}

}