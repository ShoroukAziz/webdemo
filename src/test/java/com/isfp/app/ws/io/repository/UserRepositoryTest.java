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
	final void testFindUserByLastName() {
		String lastName = "abdelaziz";
		List<UserEntity> userEntities = userRepository.findUserByLastName(lastName);
		assertNotNull(userEntities);
		assertTrue(userEntities.size()==1);
		
		UserEntity user = userEntities.get(0);
		assertEquals(user.getLastName(),lastName);
		
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
	
	@Test
	final void testFindUserBykeyword() {
		String keyword = "sho";
		List<UserEntity> userEntities = userRepository.findUserByKeyword(keyword);
		assertNotNull(userEntities);
		assertTrue(userEntities.size()==1);
		
		UserEntity user = userEntities.get(0);
		assertTrue(user.getFirstName().contains(keyword) || user.getLastName().contains(keyword));
		
	}
	
	
	@Test
	final void testFindUserFirstNameAndLastNameByKeyword() {
		String keyword = "sho";
		List<Object[]> userEntities = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
		assertNotNull(userEntities);
		assertTrue(userEntities.size()==1);
		
		Object[] user = userEntities.get(0);
		
		String userFirstName = String.valueOf(user[0]);
		String userLastName = String.valueOf(user[1]);
		
		assertNotNull(userFirstName);
		assertNotNull(userLastName);
		
		
		
	}
	
	@Test 
	final void testUpdateUserEmailVerificationStatus()
	{
		boolean newEmailVerificationStatus = true;
		userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "57TFYFGY");
		
		UserEntity storedUserDetails = userRepository.findByUserId("57TFYFGY");
		
		boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
		
		assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);

	}
	
	
	@Test 
	final void testFindUserEntityByUserId()
	{
		String userId = "57TFYFGY";
		UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
		
		assertNotNull(userEntity);
		assertTrue(userEntity.getUserId().equals(userId));
	}
	
	@Test
	final void testGetUserEntityFullNameById()
	{
		String userId = "57TFYFGY";
		List<Object[]> records =  userRepository.getUserEntityFullNameById(userId);
		
        assertNotNull(records);
        assertTrue(records.size() == 1);
        
        Object[] userDetails = records.get(0);
      
        String firstName = String.valueOf(userDetails[0]);
        String lastName = String.valueOf(userDetails[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
	}
	
	
	
	@Test 
	final void testUpdateUserEntityEmailVerificationStatus()
	{
		boolean newEmailVerificationStatus = true;
		userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, "57TFYFGY");
		
		UserEntity storedUserDetails = userRepository.findByUserId("57TFYFGY");
		
		boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
		
		assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);

	}
	
	private void createRecords() {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("shorouk");
		userEntity.setLastName("abdelaziz");
		userEntity.setEmail("mail@mail.com");
		userEntity.setUserId("57TFYFGY");
		userEntity.setEncryptedPassword("8h5h7d");
		userEntity.setEmailVerificationStatus(true);
		

		
		List<AddressEntity> addresses = new ArrayList<>();
		AddressEntity address = new AddressEntity();
		address.setAddressId("ERTUJFDKG");
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
