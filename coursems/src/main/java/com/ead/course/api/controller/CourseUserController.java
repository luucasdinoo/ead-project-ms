package com.ead.course.api.controller;

import com.ead.course.api.model.SubscriptionDTO;
import com.ead.course.api.model.UserDTO;
import com.ead.course.domain.model.entity.CourseModel;
import com.ead.course.domain.model.entity.CourseUserModel;
import com.ead.course.domain.model.enums.UserStatus;
import com.ead.course.domain.service.interfaces.CourseService;
import com.ead.course.domain.service.interfaces.CourseUserService;
import com.ead.course.infra.clients.AuthUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses/{courseId}/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    private AuthUserClient authUserClient;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseUserService courseUserService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(@PathVariable("courseId") UUID courseId,
                                                               @PageableDefault(sort = "userId") Pageable pageable) {
        Page<UserDTO> dtoPages = authUserClient.getAllUsersByCourse(courseId, pageable);
        return ResponseEntity.ok(dtoPages);
    }

    @PostMapping("/subscription")
    public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                          @RequestBody @Valid SubscriptionDTO subscriptionDTO){
        ResponseEntity<UserDTO> responseUser;
        Optional<CourseModel> courseOpt = courseService.findById(courseId);
        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }
        if (courseUserService.existsByCourseAndUserId(courseOpt.get(), subscriptionDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists");
        }
        try {
            responseUser = authUserClient.getOneUserById(subscriptionDTO.getUserId());
            if (responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: User is blocked");
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        }
        CourseUserModel courseUserModel = courseUserService
                .saveAndSendSubscriptionUserInCourse(courseOpt.get().convertToCourseUserModel(subscriptionDTO.getUserId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(courseUserModel);
    }
}
