package xiaohu.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import xiaohu.dao.RoleDao;
import xiaohu.domain.Role;


import java.util.List;

public class RoleDaoImpl implements RoleDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Role> findAll() {
        try {
            String sql="select * from sys_role";
            List<Role> roleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
            return roleList;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(Role role) {
         jdbcTemplate.update("insert into sys_role values (?,?,?) ", null, role.getRoleName(), role.getRoleDesc());

    }

    public List<Role> findByUserId(Long id) {
       String sql="select * from sys_user_role ur ,sys_user r where ur.roleId=r.id and ur.userId=?";
        List<Role> roles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class), id);
        return roles;
    }
}
