package com.ead.course.api.controller;

import com.ead.course.api.model.SubscriptionDTO;
import com.ead.course.api.model.UserDTO;
import com.ead.course.infra.clients.CourseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/courses/{courseId}/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    private CourseClient courseClient;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(@PathVariable("courseId") UUID courseId,
                                                               @PageableDefault(sort = "userId") Pageable pageable) {
        Page<UserDTO> dtoPages = courseClient.getAllUsersByCourse(courseId, pageable);
        return ResponseEntity.ok(dtoPages);
    }

    @PostMapping("/subscription")
    public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                          @RequestBody @Valid SubscriptionDTO subscriptionDTO){

    }
}
