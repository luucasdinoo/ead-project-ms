package com.ead.authuser.api.controller;

import com.ead.authuser.api.model.dto.UserDTO;
import com.ead.authuser.api.model.view.UserView;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.model.enums.UserStatus;
import com.ead.authuser.domain.model.enums.UserType;
import com.ead.authuser.domain.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Validated(UserView.RegistrationPost.class)
                                              @JsonView(UserView.RegistrationPost.class) UserDTO dto) {
        log.debug("POST registerUser userDTO received {}", dto.toString());
        if (userService.existsByUsername(dto.getUsername())) {
            log.warn("Username {} is Already Taken", dto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken");
        }

        if (userService.existsByEmail(dto.getEmail())) {
            log.warn("Email {} is Already Taken", dto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken");
        }
        var user = new UserModel();
        BeanUtils.copyProperties(dto, user);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        UserModel savedUser = userService.save(user);
        log.debug("POST registerUser userId saved {}", savedUser.getUserId());
        log.info("User saved successfully userId {}", savedUser.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
