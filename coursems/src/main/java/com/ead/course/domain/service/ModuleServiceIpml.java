package com.ead.course.domain.service;

import com.ead.course.domain.model.entity.LessonModel;
import com.ead.course.domain.model.entity.ModuleModel;
import com.ead.course.domain.repository.LessonRepository;
import com.ead.course.domain.repository.ModuleRepository;
import com.ead.course.domain.service.interfaces.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleServiceIpml implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(ModuleModel moduleModel) {
        List<LessonModel> lessons = lessonRepository.findAllLessonsIntoModule(moduleModel.getModeuleId());
        if (!lessons.isEmpty())
            lessonRepository.deleteAll(lessons);

        moduleRepository.delete(moduleModel);
    }

    @Override
    @Transactional
    public ModuleModel save(ModuleModel module) {
        return moduleRepository.save(module);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    @Override
    public List<ModuleModel> findAllByCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }
}
