package com.hbsi.user.controller;


import com.hbsi.user.entity.User;
import com.hbsi.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.Api;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-12
 */
@Controller
@RequestMapping("/users/")
@Api(tags = "用户模块")
@RequiredArgsConstructor
public class UserController {




	private UserServiceImpl userService;

	@Autowired
	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	/**
	 * 分页查询用户
	 * @param model model and view
	 * @return 页面
	 */
	@GetMapping("getAllUsers")
	public String getUsersByPage(Integer current,Integer size,Model model) {
		if(ObjectUtils.isEmpty(current)){
			model.addAttribute("msgFiled","当前页参数为空");
			return userService.getUsers(1,8,model);
		}
		if(ObjectUtils.isEmpty(size)){
			model.addAttribute("msgFiled","每页数量参数为空");
			return userService.getUsers(1,8,model);
		}
		model.addAttribute("msg","查询用户成功");
		return userService.getUsers(current,size,model);
	}





	/**
	 * 保存用户
	 * @return 页面
	 */
	@PostMapping("saveUser")
	public String saveUser(User user,Model model) {
		boolean flag = userService.saveUser(user, model);
		if (flag){
			model.addAttribute("msg","保存用户成功");
		}else{
			model.addAttribute("msgFiled","保存用户失败");
		}
		return userService.getUsers(1,8,model);
	}

	/**
	 * 根据用户id查询用户
	 * @param id 用户id
	 * @param model model and view
	 * @return 页面
	 */
	@GetMapping("lookUser")
	public String lookUser(@RequestParam("id") Integer id ,Model model) {
		if(ObjectUtils.isEmpty(id)){
			model.addAttribute("msgFiled","id为空");
			return userService.getUsers(1,8,model);
		}
		User user = userService.getUserById(id);
		model.addAttribute("user",user);
		return "user/update.html";
	}


	/**
	 * 映射到添加用户页面
	 * @return 页面
	 */
	@GetMapping("add")
	public String addPage() {
		return "user/add.html";
	}

	/**
	 *修改功能
	 * @return 页面
	 */
	@PostMapping("updateUser")
	public String updateUser(User user,Model model) {
		boolean flag = userService.updateUser(user);
		if (!flag){
			model.addAttribute("msgFiled","修改用户失败");
			return userService.getUsers(1,8,model);
		}
		model.addAttribute("msg","修改用户成功");
		return userService.getUsers(1,8,model);
	}


	/**
	 * 删除用户
	 * @param id 用户id
	 * @param model model and view
	 * @return 页面
	 */
	@GetMapping("userDelete")
	public String userDelete(@RequestParam("id")Integer id,Model model) {
		if(ObjectUtils.isEmpty(id)){
			model.addAttribute("msgFiled","id为空");
			return userService.getUsers(1,8,model);
		}
		boolean flag = userService.deleteUserById(id);
		if (!flag){
			model.addAttribute("msgFiled","删除用户失败");
			return userService.getUsers(1,8,model);
		}
		model.addAttribute("msg","删除用户成功");
		return userService.getUsers(1,8,model);
	}


	/**
	 * 根据用户名称查询用户
	 * @param userName 用户名
	 * @param model modelAndView
	 * @return 页面
	 */
	@GetMapping("getUserByName")
	public String getUserByName(String userName,Model model){
		if (ObjectUtils.isEmpty(userName)){
			model.addAttribute("msgFiled","用户名不能为空");
			return "user/userByName.html";
		}
		User user = userService.getUserByName(userName);
		if (user == null){
			model.addAttribute("msgFiled","暂无该用户信息");
		}else{
			model.addAttribute("msg","查询用户成功");
			model.addAttribute("user",user);
		}
		return "user/userByName.html";
	}

}
