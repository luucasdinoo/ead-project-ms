package com.ead.course.domain.service;

import com.ead.course.domain.repository.LessonRepository;
import com.ead.course.domain.service.interfaces.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceIpml implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;
}
