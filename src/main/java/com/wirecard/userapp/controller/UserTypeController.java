package com.wirecard.userapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wirecard.userapp.config.OrikaMapperAutoConfig;
import com.wirecard.userapp.dto.UserTypeDTO;
import com.wirecard.userapp.response.DefaultResponse;
import com.wirecard.userapp.response.usertype.ResponseUserTypeView;
import com.wirecard.userapp.usertype.entity.UserType;
import com.wirecard.userapp.usertype.service.UserTypeService;

@RestController
@Validated
public class UserTypeController {

	@Autowired
	UserTypeService userTypeService;

	@Autowired
	OrikaMapperAutoConfig orikaMapperAutoConfig;

	@GetMapping("/usertype")
	public ResponseEntity<ResponseUserTypeView> getUserType(@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = false) String name,
			@PageableDefault(value = 8, page = 0) Pageable pageable) {

		return userTypeService.getUserTypeList(pageable, id, name);

	}

	@PostMapping("/usertype")
	public ResponseEntity<DefaultResponse> insertNewUserType(@Valid @RequestBody UserTypeDTO userTypeDTO) {

		UserType userType = orikaMapperAutoConfig.getUserTypeBeanFromUserTypeDTO(userTypeDTO);

		return userTypeService.insertNewUserType(userType);

	}

	@PutMapping("/usertype/{id}")
	public ResponseEntity<DefaultResponse> updateExistingUserTypeById(@PathVariable("id") Long id,
			@Valid @RequestBody UserTypeDTO userTypeDTO) {

		UserType userType = orikaMapperAutoConfig.getUserTypeBeanFromUserTypeDTO(userTypeDTO);

		return userTypeService.updateExistingUserTypeById(id, userType);

	}

	@DeleteMapping("/usertype/{id}")
	public ResponseEntity<DefaultResponse> deleteExistingUserTypeById(@PathVariable("id") Long id) {

		return userTypeService.deleteExistingUserTypeById(id);

	}

}
