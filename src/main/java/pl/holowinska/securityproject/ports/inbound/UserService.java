package pl.holowinska.securityproject.ports.inbound;

import pl.holowinska.securityproject.domain.model.User;

public interface UserService {

    void register(User user);

    void addRoleToUser(Long userId, Long roleId);

    User getUserById(Long id);
}
