package pl.holowinska.securityproject.adapters.rest.mappers;

import pl.holowinska.securityproject.api.model.PermissionDTO;
import pl.holowinska.securityproject.domain.model.Permission;

public class PermissionMapper {

    public static Permission mapToEntity(PermissionDTO permissionDTO) {
        if (permissionDTO == null) {
            return null;
        }
        return Permission.builder()
                .name(permissionDTO.getName())
                .build();
    }

    public static PermissionDTO mapToDTO(Permission permission) {
        if (permission == null) {
            return null;
        }
        return PermissionDTO.builder()
                .permissionId(permission.getId())
                .name(permission.getName())
                .build();
    }
}
