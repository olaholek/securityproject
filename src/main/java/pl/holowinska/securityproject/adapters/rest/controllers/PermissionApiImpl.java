package pl.holowinska.securityproject.adapters.rest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.holowinska.securityproject.adapters.rest.mappers.PermissionMapper;
import pl.holowinska.securityproject.api.PermissionApi;
import pl.holowinska.securityproject.api.model.PermissionDTO;
import pl.holowinska.securityproject.domain.model.Permission;
import pl.holowinska.securityproject.ports.inbound.PermissionService;

@RestController
@AllArgsConstructor
public class PermissionApiImpl implements PermissionApi {

    private final PermissionService permissionService;

    @Override
    public ResponseEntity<PermissionDTO> addPermission(PermissionDTO permissionDTO) {
        Permission permission = permissionService.addPermission(PermissionMapper.mapToEntity(permissionDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(PermissionMapper.mapToDTO(permission));
    }
}
