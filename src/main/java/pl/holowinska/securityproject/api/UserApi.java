package pl.holowinska.securityproject.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.holowinska.securityproject.api.model.UserDTO;
import pl.holowinska.securityproject.api.model.UserRoleDTO;

@RequestMapping("/users")
public interface UserApi {

    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('USER_ROLE_ASSIGN')")
    ResponseEntity<Void> addRoleToUser(@RequestBody UserRoleDTO userRoleDTO);

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id);
}
