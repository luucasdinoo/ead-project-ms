package com.ead.course.api.controller;

import com.ead.course.api.model.SubscriptionDTO;
import com.ead.course.domain.model.entity.CourseModel;
import com.ead.course.domain.service.interfaces.CourseService;
import com.ead.course.domain.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses/{courseId}/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsersByCourse(@PathVariable("courseId") UUID courseId,
                                                               @PageableDefault(sort = "userId") Pageable pageable) {
        Optional<CourseModel> courseOpt = courseService.findById(courseId);
        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/subscription")
    public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                          @RequestBody @Valid SubscriptionDTO subscriptionDTO){
        Optional<CourseModel> courseOpt = courseService.findById(courseId);
        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }
        // TODO
        return ResponseEntity.status(HttpStatus.CREATED).body("courseUserModel");
    }

}
