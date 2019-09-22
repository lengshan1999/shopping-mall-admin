package com.hbsi.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 * @author lenovo
 *
 */
public class LoginHandlerIntercepter implements HandlerInterceptor{
	/**
	 *目标方法执行前 
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object user = request.getSession().getAttribute("userName");//获取session 中的username
		if(user != null) {//登录
			return true;
		}else {//未登录
			request.setAttribute("msg", "没有权限访问,请先登录");
			request.getRequestDispatcher("/login.html").forward(request, response);
			return false;
		}
		
	}
}
