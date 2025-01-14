package com.ead.course.domain.service.interfaces;

import com.ead.course.domain.model.entity.CourseModel;
import com.ead.course.domain.model.entity.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {

    boolean existsByCourseAndUserId(CourseModel course, UUID userId);

    CourseUserModel save(CourseUserModel courseUserModel);

    CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel);
}
