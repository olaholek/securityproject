package pl.holowinska.securityproject.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.holowinska.securityproject.api.model.PermissionDTO;

@RequestMapping("/permissions")
public interface PermissionApi {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<PermissionDTO> addPermission(@RequestBody PermissionDTO permissionDTO);
}
