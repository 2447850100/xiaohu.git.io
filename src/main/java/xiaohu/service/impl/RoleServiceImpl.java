package xiaohu.service.impl;

import xiaohu.dao.RoleDao;
import xiaohu.domain.Role;
import xiaohu.service.RoleService;

import java.util.List;

public class RoleServiceImpl  implements RoleService {
    private RoleDao roleDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> list() {
        return roleDao.findAll();
    }

    public void save(Role role) {
         roleDao.save(role);
    }
}
