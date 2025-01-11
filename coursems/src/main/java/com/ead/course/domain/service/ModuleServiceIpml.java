package com.ead.course.domain.service;

import com.ead.course.domain.repository.ModuleRepository;
import com.ead.course.domain.service.interfaces.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceIpml implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;
}
