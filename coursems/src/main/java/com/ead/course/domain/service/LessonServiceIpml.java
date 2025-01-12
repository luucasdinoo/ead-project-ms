package com.ead.course.domain.service;

import com.ead.course.domain.model.entity.LessonModel;
import com.ead.course.domain.repository.LessonRepository;
import com.ead.course.domain.service.interfaces.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonServiceIpml implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(LessonModel lessonModel) {
        lessonRepository.delete(lessonModel);
    }
}
