package com.ead.course.domain.service;

import com.ead.course.domain.model.entity.CourseModel;
import com.ead.course.domain.model.entity.CourseUserModel;
import com.ead.course.domain.model.entity.LessonModel;
import com.ead.course.domain.model.entity.ModuleModel;
import com.ead.course.domain.repository.CourseRepository;
import com.ead.course.domain.repository.CourseUserRepository;
import com.ead.course.domain.repository.LessonRepository;
import com.ead.course.domain.repository.ModuleRepository;
import com.ead.course.domain.repository.specs.SpecificationTemplate;
import com.ead.course.domain.service.interfaces.CourseService;
import com.ead.course.infra.clients.AuthUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceIpml implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private AuthUserClient authUserClient;

    @Override
    @Transactional
    public void delete(CourseModel courseModel) {
        boolean deleteCourseUserInAuthUser = false;
        List<ModuleModel> moduleList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if (!moduleList.isEmpty()) {
            moduleList.forEach(module -> {
                List<LessonModel> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessons.isEmpty()) {
                    lessonRepository.deleteAll(lessons);
                }
            });
            moduleRepository.deleteAll(moduleList);
        }
        List<CourseUserModel> courseUserModels = courseUserRepository.findAllCourseUserIntoCourse(courseModel.getCourseId());
        if (!courseUserModels.isEmpty()) {
            courseUserRepository.deleteAll(courseUserModels);
            deleteCourseUserInAuthUser = true;
        }
        courseRepository.delete(courseModel);
        if (deleteCourseUserInAuthUser) {
            authUserClient.deleteCourseInAuthUser(courseModel.getCourseId());
        }
    }

    @Override
    @Transactional
    public CourseModel save(CourseModel course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }
}
