package net.honeyflower.lectures.springcache.loader;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.honeyflower.lectures.springcache.model.Users;
import net.honeyflower.lectures.springcache.repository.UsersRepository;

@Component
public class Loader {
	
	@Autowired
	UsersRepository usersRepository;
	
	@PostConstruct
	public void load() {
		List<Users> usersList = getList();
		usersRepository.saveAll(usersList);
	}
	
	public List<Users> getList(){
		List<Users> usersList = new ArrayList<>();
		usersList.add(new Users("Spring","HF", 123L));
		usersList.add(new Users("Fall","HG", 234L));
		return usersList;
	}
}
