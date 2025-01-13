package com.ead.course.domain.repository;

import com.ead.course.domain.model.entity.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

    @Query(value = "From ModuleModel where course.courseId = :courseId")
    List<ModuleModel> findAllModulesIntoCourse(@Param("courseId")UUID courseId);

    @Query("From ModuleModel where course.courseId = :courseId and modeuleId = :moduleId")
    Optional<ModuleModel> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);
}
