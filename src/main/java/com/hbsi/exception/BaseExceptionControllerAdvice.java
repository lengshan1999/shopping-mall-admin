package com.hbsi.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * controller 增强器
 * @author while
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionControllerAdvice {


    /**
     * 全局异常捕捉处理
	 * @param e 异常
	 * @return HTML
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView myErrorHandler(Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		if (e instanceof BaseException) {
			log.error(e.getMessage()+"自定义异常");
			modelAndView.setViewName("redirect:/errorPage/500");
			return modelAndView;
		} else {
			log.error(e.getMessage(),"[系统异常]");
			modelAndView.setViewName("redirect:/errorPage/error");
			return modelAndView;
		}

	}


}
