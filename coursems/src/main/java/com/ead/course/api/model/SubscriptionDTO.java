package com.ead.course.api.model;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SubscriptionDTO {

    @NotNull
    private UUID userId;
}
