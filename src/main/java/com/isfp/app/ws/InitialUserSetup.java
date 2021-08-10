package com.isfp.app.ws;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.isfp.app.ws.io.entity.AuthorityEntity;
import com.isfp.app.ws.io.entity.AuthorityRepository;
import com.isfp.app.ws.io.entity.RoleEntity;
import com.isfp.app.ws.io.entity.UserEntity;
import com.isfp.app.ws.io.repositories.RoleRepository;
import com.isfp.app.ws.io.repositories.UserRepository;
import com.isfp.app.ws.shared.Utils;

@Component
public class InitialUserSetup {
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event ) {
		System.out.println("From Application ready event");
		
		AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
		
		RoleEntity roleUser  = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
		RoleEntity roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority,deleteAuthority));
		
		if(roleAdmin== null) return;
		
		UserEntity adminUser = new UserEntity();
		adminUser.setFirstName("Shorouk");
		adminUser.setLastName("Abdelaziz");
		adminUser.setEmail("contact@shorouk.dev");
		adminUser.setUserId(utils.generateUserId(30));
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
		adminUser.setRoles(Arrays.asList(roleAdmin));
		
		userRepository.save(adminUser);
	}
	
	@Transactional
	private AuthorityEntity createAuthority(String name) {
		
		AuthorityEntity authority = authorityRepository.findByName(name);
		if(authority == null) {
			authority = new AuthorityEntity(name);
			authorityRepository.save(authority);
		}
		return authority;
	}
	
	@Transactional
	private RoleEntity createRole(String name , Collection<AuthorityEntity> authorities) {
		
		RoleEntity role = roleRepository.findByName(name);
		if(role == null) {
			role = new RoleEntity(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
		}
		return role;
	}

}
