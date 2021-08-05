package com.isfp.app.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.*;

import com.isfp.app.ws.service.impl.UserServiceImpl;
import com.isfp.app.ws.shared.dto.AddressDto;
import com.isfp.app.ws.shared.dto.UserDto;
import com.isfp.app.ws.ui.model.response.UserRest;

class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	UserServiceImpl userService;
	
	UserDto userDto;
	final String USER_ID = "1234JDG";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		userDto = new UserDto();
		
		userDto.setFirstName("shorouk");
		userDto.setLastName("Abdelaziz");
		userDto.setEmail("test@test.com");
		userDto.setEmailVerificationToken(null);
		userDto.setEmailVerificationStatus(Boolean.FALSE);
		userDto.setUserId(USER_ID);
		userDto.setAddresses(getAddressesDto());
		userDto.setEncryptedPassword("2347dfdfb84f");
	}

	@Test
	void testGetUser() {
		when(userService.getUserByUserId(anyString())).thenReturn(userDto);
		UserRest userRest = userController.getUser(USER_ID);
		assertNotNull(userRest);
		assertEquals(USER_ID,userRest.getUserId());
		assertEquals(userDto.getFirstName(), userRest.getFirstName());
		assertEquals(userDto.getLastName(), userRest.getLastName());
		assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size() );

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
	

}
