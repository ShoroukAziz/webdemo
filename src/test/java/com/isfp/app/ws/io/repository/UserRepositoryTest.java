package com.isfp.app.ws.io.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.isfp.app.ws.io.entity.AddressEntity;
import com.isfp.app.ws.io.entity.UserEntity;
import com.isfp.app.ws.io.repositories.UserRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;

	static Boolean recordsCreated = false;
	
	@BeforeEach
	void setUp() throws Exception{
		
		if(!recordsCreated) {
			createRecords();
		}
	}
	
	@Test
	final void testGetVerifiedUsers() {
		Pageable pageableRequest = PageRequest.of(0, 2);
		Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
		assertNotNull(pages);
		
		List<UserEntity> userEntities = pages.getContent();
		assertTrue(userEntities.size()==1);
	}
	
	@Test
	final void testFindUserByFirstName() {
		String firstName = "shorouk";
		List<UserEntity> userEntities = userRepository.findUserByFirstName(firstName);
		assertNotNull(userEntities);
		assertTrue(userEntities.size()==1);
		
		UserEntity user = userEntities.get(0);
		assertEquals(user.getFirstName(),firstName);
		
	}
	
	
	private void createRecords() {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("shorouk");
		userEntity.setLastName("Abdelaziz");
		userEntity.setEmail("mail@mail.com");
		userEntity.setUserId("djshfs887");
		userEntity.setEncryptedPassword("8h5h7d");
		userEntity.setEmailVerificationStatus(true);
		

		
		List<AddressEntity> addresses = new ArrayList<>();
		AddressEntity address = new AddressEntity();
		address.setAddressId("jkdfhg6d");
		address.setCity("Alex");
		address.setCountry("Egypt");
		address.setPostalCode("12345");
		address.setStreetName("123 street");
		address.setType("shipping");

		addresses.add(address);

		
		userEntity.setAddresses(addresses);

		
		userRepository.save(userEntity);

		
		
//		
//		UserEntity userEntity2 = new UserEntity();
//		userEntity2.setFirstName("shorouk");
//		userEntity2.setLastName("Abdelaziz");
//		userEntity2.setEmail("mail3@mail.com");
//		userEntity2.setUserId("55gfhgjg");
//		userEntity2.setEncryptedPassword("8h5h7d");
//		userEntity2.setEmailVerificationStatus(false);
//		
//		
//		
//		List<AddressEntity> addresses2 = new ArrayList<>();
//		AddressEntity address2 = new AddressEntity();
//		address.setAddressId("7rhkid");
//		address.setCity("Alex");
//		address.setCountry("Egypt");
//		address.setPostalCode("12345");
//		address.setStreetName("123 street");
//		address.setType("shipping");
//		
//		
//		addresses2.add(address2);
//		
//		userEntity2.setAddresses(addresses2);
//		
//		userRepository.save(userEntity2);
		
		recordsCreated=true;
	}

}