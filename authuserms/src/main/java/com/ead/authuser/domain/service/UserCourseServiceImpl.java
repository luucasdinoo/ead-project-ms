package com.ead.authuser.domain.service;

import com.ead.authuser.domain.repository.UserCourseRepository;
import com.ead.authuser.domain.service.interfaces.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepository;
}
