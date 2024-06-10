package pl.holowinska.securityproject.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.holowinska.securityproject.domain.exceptions.RoleNotFoundException;
import pl.holowinska.securityproject.domain.exceptions.UserNotFoundException;
import pl.holowinska.securityproject.domain.exceptions.UsernameConflictException;
import pl.holowinska.securityproject.domain.model.Role;
import pl.holowinska.securityproject.domain.model.User;
import pl.holowinska.securityproject.domain.repository.RoleRepository;
import pl.holowinska.securityproject.domain.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldReturnExceptionWhenUserWithThisUsernameAlreadyExists() {
        //given
        User user = new User();
        user.setUsername("user");

        //when
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        //then
        assertThrows(UsernameConflictException.class, () -> {
            userService.register(user);
        });
    }

    @Test
    public void shouldSaveUser() {
        //given
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");

        //when
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(roleRepository.findRoleByName(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn(any());

        //then
        userService.register(user);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void shouldThrowExceptionWhenAddRoleToNonExistentUser() {
        //given
        Long userId = 1L;
        Long roleId = 1L;

        //when
        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        //then
        assertThrows(UserNotFoundException.class, () -> {
            userService.addRoleToUser(userId, roleId);
        });
    }

    @Test
    public void shouldThrowExceptionWhenAddNonExistentRoleToUser() {
        //given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Long roleId = 1L;

        //when
        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findRoleById(roleId)).thenReturn(Optional.empty());

        //then
        assertThrows(RoleNotFoundException.class, () -> {
            userService.addRoleToUser(userId, roleId);
        });
    }

    @Test
    public void shouldAddRoleToUser() {
        // given
        Long userId = 1L;
        Long roleId = 1L;
        User user = new User();
        user.setId(userId);
        Role role = new Role();
        role.setId(roleId);

        // when
        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findRoleById(roleId)).thenReturn(Optional.of(role));

        // then
        userService.addRoleToUser(userId, roleId);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void shouldThrowExceptionWhenGetByIdNonExistentUser() {
        //given
        Long userId = 1L;

        //when
        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        //then
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
    }

    @Test
    public void shouldGetUserById() {
        //given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        //when
        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));

        //then
        assertEquals(user.getId(), userService.getUserById(userId).getId());
    }
}