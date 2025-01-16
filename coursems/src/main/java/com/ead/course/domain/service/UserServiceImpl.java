package com.ead.course.domain.service;

import com.ead.course.domain.repository.UserRepository;
import com.ead.course.domain.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
}
