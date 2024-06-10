package pl.holowinska.securityproject.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.holowinska.securityproject.domain.model.Permission;
import pl.holowinska.securityproject.domain.model.Role;
import pl.holowinska.securityproject.domain.model.User;
import pl.holowinska.securityproject.domain.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not exists by Username");
        }
        Set<Role> roles = user.get().getRoles();
        Set<String> allAuthorities = new HashSet<>();

        for (Role role : roles) {
            allAuthorities.add(role.getName());
            for (Permission permission : role.getPermissions()) {
                allAuthorities.add(permission.getName());
            }
        }

        Set<SimpleGrantedAuthority> authorities = allAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(username, user.get().getPassword(), authorities);
    }
}
