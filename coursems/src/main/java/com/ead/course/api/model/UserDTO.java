package com.ead.course.api.model;

import com.ead.course.domain.model.enums.UserStatus;
import com.ead.course.domain.model.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDTO {

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
}
