package com.ead.authuser.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class UserCourseDto {

    private UUID userId;

    @NotNull
    private UUID courseId;
}
