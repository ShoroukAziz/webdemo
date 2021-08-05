package com.isfp.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.isfp.app.ws.exceptions.UserServiceException;
import com.isfp.app.ws.io.entity.AddressEntity;
import com.isfp.app.ws.io.entity.UserEntity;
import com.isfp.app.ws.io.repositories.UserRepository;
import com.isfp.app.ws.shared.Utils;
import com.isfp.app.ws.shared.dto.AddressDto;
import com.isfp.app.ws.shared.dto.UserDto;

class UserServiceImplTest {
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	String userId = "75fd6u76g";
	String encryptedPassword = "3475634rbnfdbsf83475";
	
	UserEntity userEntity;
	UserDto userDto;

	@BeforeEach
	void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
		MockitoAnnotations.openMocks(this);
		
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("shorouk");
		userEntity.setLastName("Abdelaziz");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("test@mail.com");
		userEntity.setEmailVerificationToken("eiur456546ith8345jre");
		userEntity.setAddresses(getAddressesEntity());
		
		
		userDto = new UserDto();
		userDto.setFirstName("shorouk");
		userDto.setLastName("abdelaziz");
		userDto.setEmail("test@test.com");
		userDto.setPassword("123");
		userDto.setAddresses(getAddressesDto());
	}

	@Test
	final void testGetUser() {

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		
		UserDto userDto = userService.getUser("test@test.com");
		assertNotNull(userDto);
		assertEquals("shorouk",userDto.getFirstName());
	}
	
	@Test
	void testGetUserUserNameNotFoundException() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		
		
		assertThrows(UsernameNotFoundException.class,
				
				()->{
					userService.getUser("test@test.com");
				}
				
				);
		
	}
	
	@Test
	final void testCreateUser_CreateUserServiceException() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		
		assertThrows(UserServiceException.class,
				
				()->{
					userService.createUser(userDto);
				}
				
				);
		
	}
	
	
	@Test
	final void testCreateUser() {
				
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(utils.generateAddressId(anyInt())).thenReturn("hdjr73nd378jd8");
		when(utils.generateUserId(anyInt())).thenReturn(userId);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);		
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		


		
		UserDto storedUserDetails = userService.createUser(userDto);
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(),storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
		assertEquals(userEntity.getUserId(),storedUserDetails.getUserId());
		assertEquals(userEntity.getAddresses().size(),storedUserDetails.getAddresses().size());
		verify(utils,times(2)).generateAddressId(30);
		verify(bCryptPasswordEncoder,times(1)).encode("123");
		verify(userRepository,times(1)).save(any(UserEntity.class));
	}
	
	
	private List<AddressDto> getAddressesDto() {
		AddressDto addressDto = new AddressDto();
		addressDto.setCity("Cairo");
		addressDto.setCountry("Egypt");
		addressDto.setType("Shipping");
		addressDto.setStreetName("salah salem");
		addressDto.setPostalCode("21525");

		AddressDto billingAddressDto = new AddressDto();
		billingAddressDto.setCity("Cairo");
		billingAddressDto.setCountry("Egypt");
		billingAddressDto.setType("Billing");
		billingAddressDto.setStreetName("salah salem");
		billingAddressDto.setPostalCode("21525");

		List<AddressDto> addresses = new ArrayList<>();
		addresses.add(addressDto);
		addresses.add(billingAddressDto);

		return addresses;
	}
	
	private List<AddressEntity> getAddressesEntity(){
		
		List<AddressDto> addresses = new ArrayList<>();
		java.lang.reflect.Type listType =  new TypeToken<List<AddressEntity>>() {}.getType();
		
		return new ModelMapper().map(addresses,listType);
	}
	

}
