package com.ead.course.domain.service;

import com.ead.course.domain.model.entity.LessonModel;
import com.ead.course.domain.repository.LessonRepository;
import com.ead.course.domain.service.interfaces.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceIpml implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(LessonModel lessonModel) {
        lessonRepository.delete(lessonModel);
    }

    @Override
    public LessonModel save(LessonModel lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId);
    }

    @Override
    public List<LessonModel> findAllByModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }
}
