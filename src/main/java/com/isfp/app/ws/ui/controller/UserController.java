package com.isfp.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isfp.app.ws.exceptions.UserServiceException;
import com.isfp.app.ws.service.AddressService;
import com.isfp.app.ws.service.UserService;
import com.isfp.app.ws.shared.dto.AddressDto;
import com.isfp.app.ws.shared.dto.UserDto;
import com.isfp.app.ws.ui.model.request.UserDetailsRequestModel;
import com.isfp.app.ws.ui.model.response.AddressRest;
import com.isfp.app.ws.ui.model.response.ErrorMessages;
import com.isfp.app.ws.ui.model.response.OperationStatusModel;
import com.isfp.app.ws.ui.model.response.RequestOperationName;
import com.isfp.app.ws.ui.model.response.RequestOperationStatus;
import com.isfp.app.ws.ui.model.response.UserRest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")

// to convert outgoing Java object into a JSON response
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AddressService addressService;

	// get user by id
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {

		UserDto userDto = userService.getUserByUserId(id);
		return new ModelMapper().map(userDto, UserRest.class);
	}

	// Sign up
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);

		UserRest returnValue = new UserRest();
		returnValue = modelMapper.map(createdUser, UserRest.class);

		return returnValue;
	}

	// update user
	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
							  , produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

		return new ModelMapper().map(userService.updateUser(id, new ModelMapper().map(userDetails, UserDto.class)), UserRest.class);
	}

	// delete user
	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {

		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

	// get all users
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE
							, MediaType.APPLICATION_JSON_VALUE
							,"application/hal+json" })
	
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {

		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			returnValue.add(new ModelMapper().map(userDto, UserRest.class));
		}

		return returnValue;
	}

	// get all addresses of a user by user by id
	@GetMapping(path = "/{id}/address", produces = { MediaType.APPLICATION_XML_VALUE
													, MediaType.APPLICATION_JSON_VALUE
													,"application/hal+json"})
	
	public CollectionModel <AddressRest> getUserAddresses(@PathVariable String id) {

		List<AddressRest> addressesListRestModel = new ArrayList<>();

		List<AddressDto> addressDto = addressService.getAddresses(id);

		if (addressDto != null && !addressDto.isEmpty()) {

			java.lang.reflect.Type listType = new TypeToken<List<AddressRest>>() {
			}.getType();

			addressesListRestModel = new ModelMapper().map(addressDto, listType);

		}
		
		for(AddressRest addressRest :addressesListRestModel) {
			Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id, addressRest.getAddressId())).withSelfRel();
			addressRest.add(addressLink);
			
			Link userLink = linkTo(methodOn(UserController.class).getUser(id)).withRel("user");
			addressRest.add(userLink);
		}

		return  CollectionModel.of(addressesListRestModel);
	}

	
	// get  addresses details of a user by address id
	@GetMapping(path = "/{id}/address/{addressId}",
				produces = { MediaType.APPLICATION_XML_VALUE
							,MediaType.APPLICATION_JSON_VALUE,
							"application/hal+json"})
	
	public EntityModel<AddressRest>  getUserAddress(@PathVariable String id , @PathVariable String addressId) {
		
		Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id, addressId)).withSelfRel();
		Link userLink = linkTo(UserController.class).slash(id).withRel("user");
		Link addressesLink = linkTo(methodOn(UserController.class).getUserAddresses(addressId)).withRel("addresses");
		
		AddressRest AddressRestModel =new ModelMapper().map(addressService.getAddress(addressId), AddressRest.class);
		AddressRestModel.add(addressLink).add(userLink).add(addressesLink);
		
		return EntityModel.of(AddressRestModel);

	}
	
}
