package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//	/**
//	 * 统一异常处理
//	 * @param e
//	 * @return
//	 */
//	@ExceptionHandler(Exception.class)//指定出现什么异常执行这个方法
//	@ResponseBody
//	public result error(Exception e){
//		e.printStackTrace();
//		return result.error().message("执行了统一异常");
//	}
//
//	/**
//	 * 特定异常处理
//	 * @param e
//	 * @return
//	 */
//	@ExceptionHandler(ArithmeticException.class)//指定出现什么异常执行这个方法
//	@ResponseBody
//	public result error(ArithmeticException e){
//		e.printStackTrace();
//		return result.error().message("执行了ArithmeticException异常");
//	}

	/**
	 * 自定义异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(GuLiException.class)//指定出现什么异常执行这个方法
	@ResponseBody
	public result error(GuLiException e){
		e.printStackTrace();
		log.error(e.getMessage());
		return result.error().message(e.getMsg()).code(e.getCode());
	}
}