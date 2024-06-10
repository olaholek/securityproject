package pl.holowinska.securityproject.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionRoleDTO {

    @NotNull
    private Long roleId;

    @NotNull
    private Long permissionId;
}
