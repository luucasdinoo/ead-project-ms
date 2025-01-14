package com.ead.course.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CourseUserDTO {

    private UUID courseId;
    private UUID userId;
}
