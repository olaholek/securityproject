package pl.holowinska.securityproject.adapters.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.holowinska.securityproject.adapters.rest.mappers.UserMapper;
import pl.holowinska.securityproject.api.UserApi;
import pl.holowinska.securityproject.api.model.UserDTO;
import pl.holowinska.securityproject.api.model.UserRoleDTO;
import pl.holowinska.securityproject.domain.model.User;
import pl.holowinska.securityproject.ports.inbound.UserService;

@RestController
@AllArgsConstructor
public class UserApiImpl implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<Void> addRoleToUser(UserRoleDTO userRoleDTO) {
        userService.addRoleToUser(userRoleDTO.getUserId(), userRoleDTO.getRoleId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserMapper.mapEntityToDTO(user));
    }
}
