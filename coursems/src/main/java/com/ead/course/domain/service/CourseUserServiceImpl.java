package com.ead.course.domain.service;

import com.ead.course.domain.model.entity.CourseModel;
import com.ead.course.domain.model.entity.CourseUserModel;
import com.ead.course.domain.repository.CourseUserRepository;
import com.ead.course.domain.service.interfaces.CourseUserService;
import com.ead.course.infra.clients.AuthUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private AuthUserClient authUserClient;

    @Override
    public boolean existsByCourseAndUserId(CourseModel course, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(course, userId);
    }

    @Override
    @Transactional
    public CourseUserModel save(CourseUserModel courseUserModel) {
        return courseUserRepository.save(courseUserModel);
    }

    @Override
    @Transactional
    public CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel) {
        courseUserModel = courseUserRepository.save(courseUserModel);

        authUserClient.postSubscriptionUserInCourse(
                courseUserModel.getCourse().getCourseId(), courseUserModel.getUserId());

        return courseUserModel;
    }
}
