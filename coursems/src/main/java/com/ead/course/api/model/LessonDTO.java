package com.ead.course.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LessonDTO {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String videoUrl;
}
