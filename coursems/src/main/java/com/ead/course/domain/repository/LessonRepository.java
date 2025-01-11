package com.ead.course.domain.repository;

import com.ead.course.domain.model.entity.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonModel, UUID> {
}
