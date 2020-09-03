package xiaohu.service.impl;

import xiaohu.dao.RoleDao;
import xiaohu.dao.UserDao;
import xiaohu.domain.Role;
import xiaohu.domain.User;
import xiaohu.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    private RoleDao roleDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<User> list() {
        //此user并没有role中的数据
      List<User>userList=  userDao.findAll();
      //遍历每个userList得到每个user对象
        for (User user : userList) {
            //根据user得到对应的ID
            Long id = user.getId();
            //根据user的Id获得对应role对象
          List<Role>roles=  roleDao.findByUserId(id);
          //将 role对象数据添加到user中
          user.setRoles(roles);
        }
        return userList;
    }

    public void save(User user, Long[] roleIds) {
        //第一步，向sys_user表中存储数据
        Long userId = userDao.save(user);
        //第二步，向关系表中存储多条数据
        userDao.saveUserRoleRel(userId,roleIds);
    }

    public void delUser(long userId) {
        //删除sys_user_role
           userDao.delUserRoleRel(userId);

        //删除sys_user
        userDao.delUser(userId);
    }

    public User login(String username, String password) {

        return userDao.findByUsernameAndPassword(username,password);
    }
}
