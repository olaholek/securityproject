package pl.holowinska.securityproject.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.holowinska.securityproject.domain.exceptions.PermissionNotFoundException;
import pl.holowinska.securityproject.domain.exceptions.RoleNotFoundException;
import pl.holowinska.securityproject.domain.model.Permission;
import pl.holowinska.securityproject.domain.model.Role;
import pl.holowinska.securityproject.domain.repository.PermissionRepository;
import pl.holowinska.securityproject.domain.repository.RoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void shouldThrowExceptionWhenRoleNameDoesNotHaveCorrectPrefix() {
        //given
        Role role = new Role();
        role.setName("MANAGER");

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            roleService.addRole(role);
        });
    }

    @Test
    public void shouldThrowExceptionWhenRoleWithThisNameAlreadyExists() {
        //given
        Role role = new Role();
        role.setName("ROLE_MANAGER");

        //when
        when(roleRepository.existsByName(role.getName())).thenReturn(true);

        //then
        assertThrows(IllegalStateException.class, () -> {
            roleService.addRole(role);
        });
    }

    @Test
    public void shouldReturnSuccessfulSavedRole() {
        //given
        Role role = new Role();
        role.setName("ROLE_MANAGER");

        //when
        when(roleRepository.save(role)).thenReturn(role);

        //then
        assertEquals(role.getName(), roleService.addRole(role).getName());
    }

    @Test
    public void shouldThrowRoleNotFoundExceptionWhenRoleDoesNotExist() {
        // given
        Long roleId = 1L;
        Long permissionId = 1L;

        // when
        when(roleRepository.findRoleById(roleId)).thenReturn(Optional.empty());

        // then
        assertThrows(RoleNotFoundException.class, () -> {
            roleService.addPermissionToRole(permissionId, roleId);
        });
    }

    @Test
    public void shouldThrowPermissionNotFoundExceptionWhenPermissionDoesNotExist() {
        // given
        Long roleId = 1L;
        Role role = new Role();
        role.setId(roleId);
        Long permissionId = 1L;

        // when
        when(permissionRepository.findPermissionById(permissionId)).thenReturn(Optional.empty());
        when(roleRepository.findRoleById(roleId)).thenReturn(Optional.of(role));

        // then
        assertThrows(PermissionNotFoundException.class, () -> {
            roleService.addPermissionToRole(permissionId, roleId);
        });
        verify(roleRepository, times(0)).save(any());
    }

    @Test
    public void shouldAddPermissionToRole() {
        // given
        Long roleId = 1L;
        Long permissionId = 1L;
        Role role = new Role();
        role.setId(roleId);
        Permission permission = new Permission();
        permission.setId(permissionId);

        // when
        when(roleRepository.findRoleById(roleId)).thenReturn(Optional.of(role));
        when(permissionRepository.findPermissionById(permissionId)).thenReturn(Optional.of(permission));

        // then
        roleService.addPermissionToRole(permissionId, roleId);
        verify(roleRepository, times(1)).save(any());
    }
}