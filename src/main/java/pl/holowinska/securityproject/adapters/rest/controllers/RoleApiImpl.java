package pl.holowinska.securityproject.adapters.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.holowinska.securityproject.adapters.rest.mappers.RoleMapper;
import pl.holowinska.securityproject.api.RoleApi;
import pl.holowinska.securityproject.api.model.PermissionRoleDTO;
import pl.holowinska.securityproject.api.model.RoleDTO;
import pl.holowinska.securityproject.domain.model.Role;
import pl.holowinska.securityproject.ports.inbound.RoleService;

@RestController
@AllArgsConstructor
public class RoleApiImpl implements RoleApi {

    private final RoleService roleService;

    @Override
    public ResponseEntity<RoleDTO> addRole(RoleDTO roleDTO) {
        Role role = roleService.addRole(RoleMapper.mapToEntity(roleDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleMapper.mapToDTO(role));
    }

    @Override
    public ResponseEntity<Void> addPermissionToRole(PermissionRoleDTO permissionRoleDTO) {
        roleService.addPermissionToRole(permissionRoleDTO.getPermissionId(), permissionRoleDTO.getRoleId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
