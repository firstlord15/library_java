package org.ratmir.project.dto.moderation;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ModerationActionDTO {
    @Size(max = 5000)
    private String comment;
}
