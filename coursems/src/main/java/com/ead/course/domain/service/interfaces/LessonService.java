package com.ead.course.domain.service.interfaces;

import com.ead.course.domain.model.entity.LessonModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {

    void delete(LessonModel lessonModel);

    LessonModel save(LessonModel lesson);

    Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);

    List<LessonModel> findAllByModule(UUID moduleId);
}
