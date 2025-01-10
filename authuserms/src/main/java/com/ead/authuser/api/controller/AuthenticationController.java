package com.ead.authuser.api.controller;

import com.ead.authuser.api.model.dto.UserDTO;
import com.ead.authuser.api.model.view.UserView;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.model.enums.UserStatus;
import com.ead.authuser.domain.model.enums.UserType;
import com.ead.authuser.domain.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Validated(UserView.RegistrationPost.class)
                                              @JsonView(UserView.RegistrationPost.class) UserDTO dto) {
        if (userService.existsByUsername(dto.getUsername()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken");

        if (userService.existsByEmail(dto.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken");

        var user = new UserModel();
        BeanUtils.copyProperties(dto, user);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        UserModel savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
