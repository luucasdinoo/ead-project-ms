package com.ead.authuser.domain.service;

import com.ead.authuser.domain.service.interfaces.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    @Override
    public String createUrlGetAllCoursesByUser(UUID userId, Pageable pageable){
        return "/courses?userId=" + userId
                + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize()
                + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }
}
