package xiaohu.service;

import xiaohu.domain.User;

import java.util.List;

public interface UserService {
    List<User> list();

    void save(User user, Long[] roleIds);

    void delUser(long userId);

    User login(String username, String password);
}
