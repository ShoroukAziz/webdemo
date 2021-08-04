package com.isfp.app.ws.service;

import java.util.List;

import com.isfp.app.ws.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto>  getAddresses(String userId);
	AddressDto getAddress(String addressId);

}
