package xiaohu.dao;

import xiaohu.domain.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    Long save(User user);

    void saveUserRoleRel(Long id, Long[] roleIds);

    void delUser(long userId);

    void delUserRoleRel(long userId);

    User findByUsernameAndPassword(String username,String password);
}
