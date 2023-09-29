package com.convertAPISpec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.convertAPISpec.service.APIConverterDTO;
import com.convertAPISpec.service.APIConverterService;


@RestController
public class ConverterController {

	@Autowired
	private APIConverterService apiConverterService;
	
	
	@RequestMapping(value = "/api/convertAPISpec", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<APIConverterDTO> updateUser(@RequestBody APIConverterDTO apiConverterDTO) {
		//user.setId(userId);
		APIConverterDTO runApplication = apiConverterService.runConverter(apiConverterDTO);
		return new ResponseEntity<>(runApplication, HttpStatus.OK);
	}

}
