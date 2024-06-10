package pl.holowinska.securityproject.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.holowinska.securityproject.domain.exceptions.RoleNotFoundException;
import pl.holowinska.securityproject.domain.exceptions.UserNotFoundException;
import pl.holowinska.securityproject.domain.exceptions.UsernameConflictException;
import pl.holowinska.securityproject.domain.model.Role;
import pl.holowinska.securityproject.domain.model.User;
import pl.holowinska.securityproject.domain.repository.RoleRepository;
import pl.holowinska.securityproject.domain.repository.UserRepository;
import pl.holowinska.securityproject.ports.inbound.UserService;

import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameConflictException("User with this username already exists");
        }

        HashSet<Role> roles = new HashSet<>();
        String roleName = "ROLE_USER";
        Optional<Role> defaultRole = roleRepository.findRoleByName(roleName);

        if (defaultRole.isPresent()) {
            roles.add(defaultRole.get());
            user.setRoles(roles);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with the given id does not exist");
        }

        Optional<Role> optionalRole = roleRepository.findRoleById(roleId);
        if (optionalRole.isEmpty()) {
            throw new RoleNotFoundException("Role with the given id does not exist");
        }

        User user = optionalUser.get();
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(optionalRole.get());
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with the given id does not exist");
        }

        return optionalUser.get();
    }
}
