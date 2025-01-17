package com.ead.course.domain.service.interfaces;

import com.ead.course.domain.model.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    UserModel save(UserModel user);

    void delete(UUID userId);

    Optional<UserModel> findById(UUID userInstructor);
}
