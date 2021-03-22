package repository.security;

import model.Right;
import model.Role;
import model.User;

import java.util.List;

public interface RightsRolesRepository {

    void addRole(String role);

    void addRight(String right);

    Role findRoleByTitle(String role);

    Role findRoleById(Long roleId);

    Right findRightByTitle(String right);

    Right findRightById(Long id);

    void addRolesToUser(User user, List<Role> roles);

    List<Role> findAllRoles();

    List<Role> findRolesForUser(Long userId);

    void removeUserRoles(Long userId);

    void addRoleRight(Long roleId, Long rightId);

    void removeAll();
}
