package pl.holowinska.securityproject.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.holowinska.securityproject.api.model.PermissionRoleDTO;
import pl.holowinska.securityproject.api.model.RoleDTO;

@RequestMapping("/roles")
public interface RoleApi {

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    ResponseEntity<RoleDTO> addRole(@RequestBody RoleDTO roleDTO);

    @PostMapping("/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> addPermissionToRole(@RequestBody PermissionRoleDTO permissionRoleDTO);
}
