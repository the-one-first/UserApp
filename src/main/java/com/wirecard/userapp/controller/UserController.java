package com.wirecard.userapp.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.wirecard.userapp.dto.UserDTO;
import com.wirecard.userapp.response.DefaultResponse;
import com.wirecard.userapp.response.user.ResponseUserView;
import com.wirecard.userapp.user.entity.User;
import com.wirecard.userapp.user.service.UserService;

@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OrikaMapperAutoConfig orikaMapperAutoConfig;

    @GetMapping("/user")
    public ResponseEntity<ResponseUserView> getUser(@RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "userDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date userDate,
            @RequestParam(value = "typeId", required = false) Long userType,
            @PageableDefault(value = 5, page = 0) Pageable pageable) {

        return userService.getUserList(pageable, id, name, userDate, userType);

    }

    @PostMapping("/user")
    public ResponseEntity<DefaultResponse> insertNewUser(@Valid @RequestBody UserDTO userDTO) {

        User user = orikaMapperAutoConfig.getUserBeanFromUserDTO(userDTO);

        return userService.insertNewUser(user);

    }

    @PutMapping("/user/{id}")
    public ResponseEntity<DefaultResponse> updateExistingUser(@PathVariable("id") Long id,
            @Valid @RequestBody UserDTO userDTO) {

        User user = orikaMapperAutoConfig.getUserBeanFromUserDTO(userDTO);

        return userService.updateExistingUserById(id, user);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<DefaultResponse> deleteExistingUser(@PathVariable("id") Long id) {

        return userService.deleteExistingUserById(id);

    }

}
