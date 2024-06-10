package pl.holowinska.securityproject.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.holowinska.securityproject.domain.exceptions.PermissionNotFoundException;
import pl.holowinska.securityproject.domain.exceptions.RoleNotFoundException;
import pl.holowinska.securityproject.domain.model.Permission;
import pl.holowinska.securityproject.domain.model.Role;
import pl.holowinska.securityproject.domain.repository.PermissionRepository;
import pl.holowinska.securityproject.domain.repository.RoleRepository;
import pl.holowinska.securityproject.ports.inbound.RoleService;

import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public Role addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            throw new IllegalArgumentException("Role name must starts with ROLE_ prefix");
        }
        role.setName(role.getName().toUpperCase().trim());
        if (roleRepository.existsByName(role.getName())) {
            throw new IllegalStateException("Role with this name already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public void addPermissionToRole(Long permissionId, Long roleId) {
        Optional<Role> optionalRole = roleRepository.findRoleById(roleId);
        if (optionalRole.isEmpty()) {
            throw new RoleNotFoundException("Role with the given id does not exist");
        }

        Optional<Permission> optionalPermission = permissionRepository.findPermissionById(permissionId);
        if (optionalPermission.isEmpty()) {
            throw new PermissionNotFoundException("Permission with the given id does not exist");
        }

        Role role = optionalRole.get();
        if (role.getPermissions() == null) {
            role.setPermissions(new HashSet<>());
        }
        role.getPermissions().add(optionalPermission.get());
        roleRepository.save(role);
    }
}
