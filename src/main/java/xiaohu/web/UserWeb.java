package xiaohu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import xiaohu.domain.Role;
import xiaohu.domain.User;
import xiaohu.service.RoleService;
import xiaohu.service.UserService;

import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.util.List;
@Controller
@RequestMapping("/user")
public class UserWeb {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 展示user用户数据
     * @param modelAndView
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView){
        List<User>userList=userService.list();
        modelAndView.addObject("userList",userList);
        modelAndView.setViewName("user-list");
        return modelAndView;
    }

    /**
     * 保存用户信息UI
     * @param modelAndView
     * @return
     */
    @RequestMapping("/saveUI")
    public ModelAndView saveUI(ModelAndView modelAndView){
        List<Role> roleList= roleService.list();
        modelAndView.addObject("roleList",roleList);
        modelAndView.setViewName("user-add");
        return modelAndView;
    }

    /**
     * 保存用户并携带role数据
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping("/save")
    public String save(User user,Long[]roleIds){
        userService.save(user,roleIds);
        return "redirect:/user/list";
    }

    /**
     * 根据id删除
     * @param userId
     * @return
     */
    @RequestMapping("/del/{userId}")
    public String del(@PathVariable("userId") long userId){
      userService.delUser(userId);
        return "redirect:/user/list";
    }

    /**
     * 登录方法
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session){
       User user= userService.login(username,password);
       if (user!=null){
           session.setAttribute("user",user);
           return"redirect:/index.jsp";
       }
       return"redirect:/user/list";
    }
}
