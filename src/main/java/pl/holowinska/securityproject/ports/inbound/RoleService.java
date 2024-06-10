package pl.holowinska.securityproject.ports.inbound;

import pl.holowinska.securityproject.domain.model.Role;

public interface RoleService {

    Role addRole(Role role);

    void addPermissionToRole(Long permissionId, Long roleId);
}
