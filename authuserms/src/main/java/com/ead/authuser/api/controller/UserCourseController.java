package com.ead.authuser.api.controller;

import com.ead.authuser.api.model.dto.CourseDTO;
import com.ead.authuser.domain.service.interfaces.UserCourseService;
import com.ead.authuser.infra.clients.UserClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController()
@RequestMapping("/users/{userId}/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private UserClient userClient;

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(@PathVariable("userId") UUID userId,
                                                               @PageableDefault(sort = "courseId") Pageable pageable) {
        Page<CourseDTO> dtoPages = userClient.getAllCoursesByUser(userId, pageable);
        return ResponseEntity.ok(dtoPages);
    }
}
