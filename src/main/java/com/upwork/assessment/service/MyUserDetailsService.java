package com.upwork.assessment.service;


import com.upwork.assessment.config.jwt.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.upwork.assessment.model.Users;
import com.upwork.assessment.repository.UsersRepository;
@Slf4j
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Users> userByUsername = usersRepository.findByUsername(username);
		if (userByUsername.isEmpty()) {
			log.error("Could not find user with that username: {}", username);
            throw new UsernameNotFoundException("Invalid credentials!");
		}
		Users user = userByUsername.get();
        if (user == null || !user.getUsername().equals(username)) {
        	log.error("Could not find user with that username: {}", username);
            throw new UsernameNotFoundException("Invalid credentials!");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getAuthorities().forEach(authority ->
        grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority())));
        
        return new MyUser(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities,
        		user.getFirstName(), user.getLastName(), user.getEmailAddress(), user.getBirthdate());
	}

}
