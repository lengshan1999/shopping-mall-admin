package com.hbsi.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;


/**
 * 登录拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor{


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object user = request.getSession().getAttribute("userName");
		if(user != null) {
			return true;
		}else {
			request.setAttribute("msg", "您没有访问权限,请您先登录!");
			request.getRequestDispatcher("/login.html").forward(request, response);
			return false;
		}
		
	}

}
