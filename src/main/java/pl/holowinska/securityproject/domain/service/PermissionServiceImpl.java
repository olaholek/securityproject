package pl.holowinska.securityproject.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.holowinska.securityproject.domain.model.Permission;
import pl.holowinska.securityproject.domain.repository.PermissionRepository;
import pl.holowinska.securityproject.ports.inbound.PermissionService;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission addPermission(Permission permission) {
        if (permission.getName().startsWith("ROLE_")) {
            throw new IllegalArgumentException("Permission name cannot starts with ROLE_ prefix");
        }
        permission.setName(permission.getName().toUpperCase().trim());
        if (permissionRepository.existsByName(permission.getName())) {
            throw new IllegalStateException("Permission with this name already exists");
        }
        return permissionRepository.save(permission);
    }
}
