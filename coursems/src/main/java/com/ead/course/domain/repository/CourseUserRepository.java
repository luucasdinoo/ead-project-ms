package com.ead.course.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserRepository, UUID> {
}
