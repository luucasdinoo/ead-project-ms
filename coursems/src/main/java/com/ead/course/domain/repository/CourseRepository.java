package com.ead.course.domain.repository;

import com.ead.course.domain.model.entity.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

    @Query(value = "select case when count(tcu) > 0 then true else false end " +
                   "from tb_courses_users tcu where tcu.course_id = :courseId and tcu.user_id = :userId", nativeQuery = true)
    boolean existsByCourseAndUser(UUID courseId, UUID userId);

    @Modifying
    @Query(value = "insert into tb_courses_users values (:courseId, :userId); ", nativeQuery = true)
    void saveCourseUser(UUID courseId, UUID userId);

    @Modifying
    @Query(value = "delete from tb_courses_users where course_id = :courseId", nativeQuery = true)
    void deleteCourseUserByCourse(UUID courseId);

    @Modifying
    @Query(value = "delete from tb_courses_users where user_id = :userId", nativeQuery = true)
    void deleteCourseUserByUser(UUID userId);
}
