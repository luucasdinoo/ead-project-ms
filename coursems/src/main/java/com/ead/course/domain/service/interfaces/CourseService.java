package com.ead.course.domain.service.interfaces;

import com.ead.course.domain.model.entity.CourseModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(CourseModel course);

    Optional<CourseModel> findById(UUID courseId);

    List<CourseModel> findAll();
}
