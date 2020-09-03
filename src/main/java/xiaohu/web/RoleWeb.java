package xiaohu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import xiaohu.domain.Role;
import xiaohu.service.RoleService;

import java.util.List;

@RequestMapping("/role")
@Controller
public class RoleWeb {
    @Autowired
    private RoleService roleService;

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 保存用户添加信息
     * @param role
     * @return
     */
    @RequestMapping("/save")
    public String save(Role role){
        roleService.save(role);
        return  "redirect:/role/list";

    }

    /**
     * 展示所有角色
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(){
        ModelAndView modelAndView=new ModelAndView();
         List<Role> roleList= roleService.list();
         modelAndView.addObject("roleList",roleList);
         modelAndView.setViewName("role-list");
         return  modelAndView;

    }
}
