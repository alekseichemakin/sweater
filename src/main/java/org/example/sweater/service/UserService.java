package org.example.sweater.service;

import org.example.sweater.domain.Role;
import org.example.sweater.domain.User;
import org.example.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	public boolean addUser(User user) {
		User userFromDb = userRepository.findByUsername(user.getUsername());
		if (userFromDb != null) {
			return false;
		}
		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));
		userRepository.save(user);

		return true;
	}

	public void saveUser( User user, String username, Map<String, String> form) {
		user.setUsername(username);

		Set<String> roles = Arrays.stream(Role.values())
				.map(Role::name)
				.collect(Collectors.toSet());

		user.getRoles().clear();

		for (String key : form.keySet()) {
			if (roles.contains(key))
				user.getRoles().add(Role.valueOf(key));
		}
		userRepository.save(user);
	}

	public String updateUser(User cUser, User aUser) {
		StringBuilder message = new StringBuilder();

		if (aUser.getUsername() == null || aUser.getUsername().isEmpty())
			message.append("Error: Username is empty!\n");
		else
			cUser.setUsername(aUser.getUsername());

		if (aUser.getEmail() == null || aUser.getEmail().isEmpty())
			message.append("Error: Email is empty!\n");
		else
			cUser.setEmail(aUser.getEmail());

		if (aUser.getPassword() == null || aUser.getPassword().isEmpty())
			message.append("Error: Password is empty!\n");
		else 
			cUser.setPassword(aUser.getPassword());

		userRepository.save(cUser);
		return message.toString();
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

}
