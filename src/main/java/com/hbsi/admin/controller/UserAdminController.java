package com.hbsi.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hbsi.admin.entity.UserAdmin;
import com.hbsi.admin.service.IUserAdminService;
import io.swagger.annotations.Api;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2019-08-10
 */
@Api(tags = "管理员模块")
@Controller
@RequestMapping("/admin/")
public class UserAdminController {

    private final IUserAdminService userAdminService;

    public UserAdminController(IUserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }


    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param map 存放 msg
     * @param session 存放 userName
     * @return HTML
     */
    @PostMapping("login")
    public String login(
                        @RequestParam("username")String username,
                        @RequestParam("password")String password,
                        Map<String,Object> map,
                        HttpSession session
    ) {
       if (ObjectUtils.isEmpty(username))
           map.put("msg", "用户名为空!");
        if (ObjectUtils.isEmpty(password))
            map.put("msg", "密码为空!");
        UserAdmin userAdmin = userAdminService.getOne(new QueryWrapper<UserAdmin>().eq("userName", username).eq("password", password));
        if (userAdmin != null){
            session.setAttribute("userName", username);
            session.setAttribute("id",userAdmin.getId());
            return "redirect:/index";
        }else{
            map.put("msg", "用户名或密码错误!");
            return "login";
        }
    }


    /**
     * 修改密码
     * @param model ModelAndView
     * @param id 管理员id
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return HTML
     */
    @PostMapping("updatePassword")
    public String updatePassword(Model model, Integer id, String oldPassword, String newPassword){

        UserAdmin admin = userAdminService.getOne(new QueryWrapper<UserAdmin>().eq("id", id));
        if (!admin.getPassword().equals(oldPassword)){
            model.addAttribute("msgFiled","原密码输入错误");
        }else{
            boolean flag = userAdminService.update(new UserAdmin().setPassword(newPassword), new UpdateWrapper<UserAdmin>().eq("id", id));
            if (flag){
                model.addAttribute("msg","修改密码成功");
            }else{
                model.addAttribute("msgFiled","修改密码失败");
            }
        }
        return "adminUpdPwd.html";
    }





    /**
     * 退出登录 销毁session
     * @param session 销毁session
     * @return HTML
     */
    @GetMapping("loginOut")
    public String loginOut(HttpSession session){
        session.invalidate();
        session.setMaxInactiveInterval(0);
        return "redirect:/login.html";
    }


}
