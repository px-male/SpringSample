package com.example.demo.login.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class GlobalControllAdvice {
	
	@ExceptionHandler(DataAccessException.class)
	public String DataAccessExceptonHandler(DataAccessException e, Model model) {
		
		//例外クラスのエラーをModelに登録
		model.addAttribute("error", "内部サーバエラー（DB）：GlobalControllAdvice");
		
		//例外クラスのメッセージをModelに登録
		model.addAttribute("message", "DataAccessExceptionが発生しました");
		
		//HTTPエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	
	@ExceptionHandler(Exception.class)
	public String ExceptionHundler(Exception e, Model model) {
		
		//例外クラスのエラーをModelに登録
		model.addAttribute("error", "内部サーバエラー：GlobalControllAdvice");
		 
		//例外クラスのメッセージをModelに登録
		model.addAttribute("message", "Exceptionが発生しました");
		
		//HTTPエラーコード（500）をModelに登録
		model.addAttribute("statis", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	
}
