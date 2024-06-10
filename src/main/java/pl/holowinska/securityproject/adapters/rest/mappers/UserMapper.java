package pl.holowinska.securityproject.adapters.rest.mappers;

import pl.holowinska.securityproject.api.model.RegisterDTO;
import pl.holowinska.securityproject.api.model.RoleDTO;
import pl.holowinska.securityproject.api.model.UserDTO;
import pl.holowinska.securityproject.domain.model.Role;
import pl.holowinska.securityproject.domain.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {

    public static User mapRegisterDTOToEntity(RegisterDTO registerDTO) {
        if (registerDTO == null) {
            return null;
        }
        return User.builder()
                .password(registerDTO.getPassword())
                .username(registerDTO.getUsername())
                .build();
    }

    public static UserDTO mapEntityToDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(mapRoles(user.getRoles()))
                .build();
    }

    private static Set<RoleDTO> mapRoles(Set<Role> roles) {
        Set<RoleDTO> roleDTOSet = new HashSet<>();
        if (roles != null) {
            for (Role role : roles) {
                roleDTOSet.add(RoleMapper.mapToDTO(role));
            }
        }
        return roleDTOSet;
    }
}
