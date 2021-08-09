package com.isfp.app.ws.ui.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

import com.isfp.app.ws.ui.model.request.UserLoginRequestModel;

@RestController
public class AuthenticationController {
	
	
	@ApiOperation("User login")
    @ApiResponses(value = {
    @ApiResponse(code = 200, 
            message = "Response Headers", 
            responseHeaders = {
                @ResponseHeader(name = "authorization", 
                        description = "Bearer <JWT value here>"),
                @ResponseHeader(name = "userId", 
                        description = "<Public User Id value here>")
            }
    )  
    })

	@PostMapping("/users/login")
	public void fakeLogin(@RequestBody UserLoginRequestModel loginRequestModel) {
		throw new IllegalStateException("This method should be called as it's already implemented by Spring security");
	}
}
