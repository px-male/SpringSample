package com.example.demo.login.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public String getUserDetail(@ModelAttribute SignupForm form,
			Model model, 
			@PathVariable("id") String userId ) {
		
		//ユーザID確認（デバッグ）
		System.out.println("userId = " + userId);

		//コンテンツ部分にユーザ詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/userDetail :: userDetail_contents");

		//結婚ステータス用ラジオボタンの初期化
		radioMarriage = initRadioMarriage();
		
		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMarriage", radioMarriage);
		
		//ユーザIDのチェック
		if(userId != null && userId.length() > 0) {
			//ユーザ情報の取得
			User user = userService.selectOne(userId);
			
			//Userクラスをフォームクラスに変換
			form.setUserId(user.getUserId());  //ユーザID
			form.setUserName(user.getUserName());  //ユーザ名
			form.setBirthday(user.getBirthday());  //誕生日
			form.setAge(user.getAge());  //年齢
			form.setMarriage(user.isMarriage()); //結婚ステータス
			
			//Modelに登録
			model.addAttribute("signupForm", form);
		}
		
		return "/login/homeLayout";
	}
	
	//ユーザ更新用処理
	@PostMapping(value = "/userDetail", params = "update")
	public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {
		
		System.out.println("更新ボタンの処理");
		
		//Userインスタンスの生成
		User user = new User();
		
		//フォームクラスを	Userクラスに変換
		user.setUserId(form.getUserId());
		user.setUserName(form.getUserName());
		user.setPassword(form.getPassword());
		user.setAge(form.getAge());
		user.setBirthday(form.getBirthday());
		user.setMarriage(form.isMarriage());
		
		//更新実行
		boolean result = userService.updateOne(user);
		
		if (result == true) {
			model.addAttribute("result", "更新成功");
		}else {
			model.addAttribute("result", "更新失敗");
		}
		
		return getUserList(model);
	}
	
	//ユーザ削除用処理
	@PostMapping(value="/userDetail", params="delete")
	public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {
		System.out.println("削除ボタンの処理");
		
		//削除実行
		boolean result = userService.deleteOne(form.getUserId());
		
		if(result == true) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}
		
		return getUserList(model);
		
	}
	
	
	//ログアウト用メソッド
	@PostMapping("/logout")
	public String postLogout() {
		return "redirect:/login";
	}
	
	//ユーザ一覧のCSV出力メソッド
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCSV(Model model) {
		
		//ユーザを全件取得して、CSVをサーバに保存する
		userService.userCsvOut();
		
		byte[] bytes = null;
		
		try {
			
			//サーバに保存されているsample.csvファイルをbyteで取得する
			bytes = userService.getFile("sample.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//HTTPヘッダの設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "sample.csv");
		
		//sample.csvを戻す
		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}
}
