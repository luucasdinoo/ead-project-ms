package com.ead.authuser.api.model.dto;

import com.ead.authuser.domain.model.enums.CourseLevel;
import com.ead.authuser.domain.model.enums.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourseDTO {

    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    private CourseStatus courseStatus;
    private UUID userInstructor;
    private CourseLevel courseLevel;
}
