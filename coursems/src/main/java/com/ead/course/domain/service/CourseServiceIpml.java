package com.ead.course.domain.service;

import com.ead.course.domain.repository.CourseRepository;
import com.ead.course.domain.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceIpml implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
}
