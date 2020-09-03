package xiaohu.service;

import xiaohu.domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> list();

    void save(Role role);
}
