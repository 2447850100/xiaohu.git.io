package xiaohu.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import xiaohu.dao.UserDao;
import xiaohu.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao{
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询所有user数据
     * @return
     */
    public List<User> findAll() {
        try {
            String sql="select * from sys_user";
            List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
            return userList;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存用户数据 原始方法获得自增长组建 得到一个返回的id
     * @param user
     * @return
     */
    public Long save(final User user) {
        //创建PreparedStatementCreator
        PreparedStatementCreator  psc=new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
               //使用原始jdbc完成PreparedStatement的组建
                PreparedStatement ps = connection.prepareStatement("insert into sys_user values(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setObject(1,null);
                ps.setString(2,user.getUsername());
                ps.setString(3,user.getEmail());
                ps.setString(4,user.getPassword());
                ps.setString(5,user.getPhoneNum());
                return ps;
            }
        };

       /* jdbcTemplate.update("insert into sys_user values(?,?,?,?,?)",null,
                user.getUsername(),user.getEmail(),user.getPassword(),user.getPhoneNum());*/
        //返回当前从数据库保存的userId

        //创建keyHoledr
        GeneratedKeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(psc,keyHolder);
        //获得组建Id
        long userId = keyHolder.getKey().longValue();
        return userId;
    }

    /**
     * 根据用户id获得角色id
     * @param userId
     * @param roleIds
     */
    public void saveUserRoleRel(Long userId, Long[] roleIds) {
        //userId不能为null需从user获取id
        for (Long roleId : roleIds) {
            jdbcTemplate.update("insert into sys_user_role values(?,?)",userId,roleId);
        }
    }
    /**
     * 根基id删除关系表的角色数据
     * @param userId
     */
    public void delUserRoleRel(long userId) {
      String sql="delete from sys_user_role where userId=?";
        jdbcTemplate.update(sql,userId);
    }

    /**
     * 登录方法
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username,String password) {
        String sql="select * from sys_user where username=? and password=?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        return user;
    }

    /**
     * 根据id删除sys_user表的用户数据
     * @param userid
     */
    public void delUser(long userid) {
        String sql="delete from sys_user where id=?";
        jdbcTemplate.update(sql,userid);
    }

}
