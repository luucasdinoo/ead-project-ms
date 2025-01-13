package com.ead.course.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ModuleDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;
}

