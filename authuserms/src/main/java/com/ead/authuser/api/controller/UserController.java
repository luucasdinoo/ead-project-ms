package com.ead.authuser.api.controller;

import com.ead.authuser.api.model.dto.UserDTO;
import com.ead.authuser.api.model.view.UserView;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.service.UserService;
import com.ead.authuser.domain.repository.specs.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserModel> userPage = userService.findAll(spec, pageable);
        if (!userPage.isEmpty()){
            userPage.forEach(user -> {
                user.add(linkTo(methodOn(UserController.class)
                        .getOneUser(user.getUserId())).withSelfRel());
            });
        }
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOneUser(@PathVariable UUID userId){
        Optional<UserModel> userOpt = userService.findById(userId);

        if(!userOpt.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        userOpt.get().add(linkTo(methodOn(UserController.class)
                .getOneUser(userOpt.get().getUserId())).withSelfRel());
        userOpt.get().add(linkTo(UserController.class).withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.ok(userOpt.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId){
        Optional<UserModel> userOpt = userService.findById(userId);

        if(!userOpt.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        userService.delete(userOpt.get());
        return ResponseEntity.ok("User deleted successfully");
   }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId,
                                        @RequestBody @Validated(UserView.UserPut.class)
                                        @JsonView(UserView.UserPut.class) UserDTO dto){
        Optional<UserModel> userOpt = userService.findById(userId);

        if(!userOpt.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        var user = userOpt.get();
        user.setFullName(dto.getFullName());
        user.setCpf(dto.getCpf());
        user.setPhoneNumber(dto.getPhoneNumber());
        UserModel updatedUser = userService.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable UUID userId,
                                            @RequestBody @Validated(UserView.PasswordPut.class)
                                            @JsonView(UserView.PasswordPut.class) UserDTO dto){
        Optional<UserModel> userOpt = userService.findById(userId);

        if(!userOpt.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        if (!userOpt.get().getPassword().equals(dto.getOldPassword()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Old password not matched");

        var user = userOpt.get();
        user.setPassword(dto.getPassword());
        UserModel updatedUser = userService.save(user);
        return ResponseEntity.ok("Password updated successfully");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<?> updateImage(@PathVariable UUID userId,
                                            @RequestBody @Validated(UserView.ImagePut.class)
                                            @JsonView(UserView.ImagePut.class) UserDTO dto){
        Optional<UserModel> userOpt = userService.findById(userId);

        if(!userOpt.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        var user = userOpt.get();
        user.setImageUrl(dto.getImageUrl());
        UserModel updatedUser = userService.save(user);
        return ResponseEntity.ok(updatedUser);
    }
}
