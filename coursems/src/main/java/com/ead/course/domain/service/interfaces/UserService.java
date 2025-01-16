package com.ead.course.domain.service.interfaces;

import com.ead.course.domain.model.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {
    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
}
