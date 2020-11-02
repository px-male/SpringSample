package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	//結婚ステータスのラジオボタン用変数
	private Map<String, String> radioMarriage;
	
	//結婚ボタンの初期化
	private Map<String, String> initRadioMarriage() {
		Map <String, String> radio = new LinkedHashMap();
		
		//既婚、未婚をMAPに格納
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		
		return radio;
		
	}
	
	//ホーム画面のGet用メソッド
	@GetMapping("/home")
	public String getHome(Model model) {
		
		model.addAttribute("contents", "login/home::home_contents");
		
		return "login/homeLayout";
	}
	
	//ユーザ一覧画面のGETメソッド
	@GetMapping("/userList")
	public String getUserList(Model model) {
		
		//コンテンツ部分にユーザ一覧を表示するための文字列を登録
		model.addAttribute("contents", "login/userList::userList_contents");
		
		//ユーザ一覧の生成
		List<User> userList = userService.selectMany();

		//Modelにユーザリストを登録
		model.addAttribute("userList", userList);
		
		//データ件数を取得
		int count = userService.count();
		
		model.addAttribute("userListCount", count);
		
		return "login/homeLayout";
	}
	
	//ユーザ詳細画面用のGET用メソッド
	@GetMapping("/userDetail/{id:.+}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("id") String userId ) {
		
		//ユーザID確認（デバッグ）
		System.out.println("userId = " + userId);

		//コンテンツ部分にユーザ詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/userDetail :: userDetail_contents");
		
		return "/login/homeLayout";
	}
	
	
	//ログアウト用メソッド
	@PostMapping("/logout")
	public String postLogout() {
		return "redirect:/login";
	}
	
	//ユーザ一覧のCSV出力メソッド
	@GetMapping("/userList/csv")
	public String getUserListCSV(Model model) {
		//戻る
		return getUserList(model);
	}
	
	
}
