package com.ead.course.domain.repository;

import com.ead.course.domain.model.entity.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonModel, UUID> {

    @Query("From LessonModel where module.modeuleId = :moduleId")
    List<LessonModel> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);

    @Query("From LessonModel where module.modeuleId = :moduleId and lessonId = :lessonId")
    Optional<LessonModel> findLessonIntoModule(@Param("moduleId") UUID moduleId, @Param("lessonId") UUID lessonId);
}
