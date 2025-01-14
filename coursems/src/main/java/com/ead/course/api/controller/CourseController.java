package com.ead.course.api.controller;

import com.ead.course.api.model.CourseDTO;
import com.ead.course.domain.model.entity.CourseModel;
import com.ead.course.domain.repository.specs.SpecificationTemplate;
import com.ead.course.domain.service.interfaces.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<?> saveCourse(@RequestBody @Valid CourseDTO dto){
        var course = new CourseModel();
        BeanUtils.copyProperties(dto, course);
        CourseModel savedCourse = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable UUID courseId){
        Optional<CourseModel> courseOpt = courseService.findById(courseId);
        if (courseOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");

        courseService.delete(courseOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Course Deleted Successfully");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable UUID courseId, @RequestBody @Valid CourseDTO dto){
        Optional<CourseModel> courseOpt = courseService.findById(courseId);
        if (courseOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");

        var course = courseOpt.get();
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setImageUrl(dto.getImageUrl());
        course.setCourseStatus(dto.getCourseStatus());
        course.setCourseLevel(course.getCourseLevel());
        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(course));
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec, @RequestParam(required = false) UUID userId,
                                                           @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable){
        if (userId != null)
            return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable));

        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getOneCourse(@PathVariable UUID courseId){
        Optional<CourseModel> courseOpt = courseService.findById(courseId);
        if (courseOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");

        return ResponseEntity.status(HttpStatus.OK).body(courseOpt.get());
    }
}
