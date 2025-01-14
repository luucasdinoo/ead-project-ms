package com.ead.authuser.api.controller;

import com.ead.authuser.api.model.dto.CourseDTO;
import com.ead.authuser.api.model.dto.UserCourseDto;
import com.ead.authuser.domain.model.UserCourseModel;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.service.interfaces.UserCourseService;
import com.ead.authuser.domain.service.interfaces.UserService;
import com.ead.authuser.infra.clients.CourseClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController()
@RequestMapping("/users/{userId}/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(@PathVariable("userId") UUID userId,
                                                               @PageableDefault(sort = "courseId") Pageable pageable) {
        Page<CourseDTO> dtoPages = courseClient.getAllCoursesByUser(userId, pageable);
        return ResponseEntity.ok(dtoPages);
    }

    @PostMapping("/subscription")
    public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable UUID userId, @RequestBody @Valid UserCourseDto dto) {
        Optional<UserModel> userOpt = userService.findById(userId);

        if(userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (userCourseService.existsByUserAndCourseId(userOpt.get(), dto.getCourseId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists");
        }
        UserCourseModel userCourseModel = userCourseService.save(userOpt.get().convertToUserCourseModel(dto.getCourseId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userCourseModel);
    }
}
