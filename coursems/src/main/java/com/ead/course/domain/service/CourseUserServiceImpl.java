package com.ead.course.domain.service;

import com.ead.course.domain.repository.CourseUserRepository;
import com.ead.course.domain.service.interfaces.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;
}
