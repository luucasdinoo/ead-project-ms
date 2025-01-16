package com.ead.authuser.api.controller;

import com.ead.authuser.api.model.dto.CourseDTO;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.service.interfaces.UserService;
import com.ead.authuser.infra.clients.CourseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<?> getAllCoursesByUser(@PathVariable("userId") UUID userId,
                                                               @PageableDefault(sort = "courseId") Pageable pageable) {
        Optional<UserModel> optionalUserModel = userService.findById(userId);
        if (optionalUserModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        Page<CourseDTO> dtoPages = courseClient.getAllCoursesByUser(userId, pageable);
        return ResponseEntity.ok(dtoPages);
    }
}
