package com.example.demo.login.controller;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;

@Controller
public class SignupController {
	private Map<String, String> radioMarriage;

	private Map<String, String> initRadioMarriage(){
		Map<String, String> radio = new LinkedHashMap<>();
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		return radio;
	}

	//ユーザ登録画面のget用コントローラ
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute("signupForm") SignupForm form, Model model) {

		//ラジオボタン初期化メソッド呼び出し
		radioMarriage = initRadioMarriage();
		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMarriage", radioMarriage);

		return "login/signup";
	}

	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
		//GET リクエスト用のメソッドを呼び出し、登録画面に戻る
		return getSignUp(form, model);
		}

		System.out.println(form);

		return "redirect:/login";

	}
}
