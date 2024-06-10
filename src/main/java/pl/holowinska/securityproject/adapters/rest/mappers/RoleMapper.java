package pl.holowinska.securityproject.adapters.rest.mappers;

import pl.holowinska.securityproject.api.model.RoleDTO;
import pl.holowinska.securityproject.domain.model.Role;

public class RoleMapper {

    public static Role mapToEntity(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }
        return Role.builder()
                .name(roleDTO.getName())
                .build();
    }

    public static RoleDTO mapToDTO(Role role) {
        if (role == null) {
            return null;
        }
        return RoleDTO.builder()
                .name(role.getName())
                .roleId(role.getId())
                .build();
    }
}
