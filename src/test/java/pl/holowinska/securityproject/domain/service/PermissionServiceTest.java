package pl.holowinska.securityproject.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.holowinska.securityproject.domain.model.Permission;
import pl.holowinska.securityproject.domain.repository.PermissionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Test
    public void shouldThrowExceptionWhenPermissionNameHaveUnnecessaryPrefix() {
        //given
        Permission permission = new Permission();
        permission.setName("ROLE_CREATE");

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            permissionService.addPermission(permission);
        });
    }

    @Test
    public void shouldThrowExceptionWhenPermissionWithThisNameAlreadyExists() {
        //given
        Permission permission = new Permission();
        permission.setName("CREATE_ROLE");

        //when
        when(permissionRepository.existsByName(permission.getName())).thenReturn(true);

        //then
        assertThrows(IllegalStateException.class, () -> {
            permissionService.addPermission(permission);
        });
    }

    @Test
    public void shouldReturnSuccessfulSavedPermission() {
        //given
        Permission permission = new Permission();
        permission.setName("CREATE_ROLE");

        //when
        when(permissionRepository.save(permission)).thenReturn(permission);

        //then
        assertEquals(permission.getName(), permissionService.addPermission(permission).getName());
    }
}