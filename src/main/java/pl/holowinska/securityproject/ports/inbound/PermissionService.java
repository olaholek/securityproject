package pl.holowinska.securityproject.ports.inbound;

import pl.holowinska.securityproject.domain.model.Permission;

public interface PermissionService {

    Permission addPermission(Permission permission);
}
