package com.ead.authuser.domain.service;

import com.ead.authuser.domain.model.UserCourseModel;
import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.repository.UserCourseRepository;
import com.ead.authuser.domain.service.interfaces.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Override
    public boolean existsByUserAndCourseId(UserModel userModel, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(userModel, courseId);
    }

    @Override
    @Transactional
    public UserCourseModel save(UserCourseModel userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }

    @Override
    public boolean existsByCourseId(UUID courseId) {
        return userCourseRepository.existsByCourseId(courseId);
    }

    @Override
    @Transactional
    public void deleteUserCourseByCourse(UUID courseId) {
        userCourseRepository.deleteAllByCourseId(courseId);
    }
}
