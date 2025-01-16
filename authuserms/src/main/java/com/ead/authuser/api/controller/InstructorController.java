package com.ead.authuser.api.controller;

import com.ead.authuser.api.model.dto.InstructorDTO;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.model.enums.UserType;
import com.ead.authuser.domain.service.interfaces.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/instructors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InstructorController {

    @Autowired
    private UserService userService;

    @PostMapping("/subscription")
    public ResponseEntity<?> saveSubscriptionInstructor(@RequestBody @Valid InstructorDTO dto){
        Optional<UserModel> userOpt = userService.findById(dto.getUserId());
        if(userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        }else {
            var userModel = userOpt.get();
            userModel.setUserType(UserType.INSTRUCTOR);
            userService.save(userModel);
            return ResponseEntity.ok().body(userModel);
        }
    }
}
