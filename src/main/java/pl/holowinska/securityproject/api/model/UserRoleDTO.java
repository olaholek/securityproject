package pl.holowinska.securityproject.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRoleDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long roleId;
}
