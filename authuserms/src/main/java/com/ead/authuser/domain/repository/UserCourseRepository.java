package com.ead.authuser.domain.repository;

import com.ead.authuser.domain.model.UserCourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {}
