package com.ead.authuser.domain.service;

import com.ead.authuser.domain.model.UserModel;
import com.ead.authuser.domain.model.enums.ActionType;
import com.ead.authuser.domain.repository.UserRepository;
import com.ead.authuser.domain.service.interfaces.UserService;
import com.ead.authuser.infra.queue.publisher.UserEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEventPublisher userEventPublisher;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional
    public void delete(UserModel user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public void deleteUser(UserModel user) {
        delete(user);
        userEventPublisher.publishUserEvent(user.convertToUserEventDTO(), ActionType.DELETE);
    }

    @Transactional
    @Override
    public UserModel updateUser(UserModel user) {
        UserModel saveUser = save(user);
        userEventPublisher.publishUserEvent(saveUser.convertToUserEventDTO(), ActionType.UPDATE);
        return user;    }

    @Transactional
    @Override
    public UserModel updatePassword(UserModel user) {
        return save(user);
    }

    @Transactional
    @Override
    public UserModel saveUser(UserModel user) {
        UserModel saveUser = save(user);
        userEventPublisher.publishUserEvent(saveUser.convertToUserEventDTO(), ActionType.CREATE);
        return user;
    }
}
