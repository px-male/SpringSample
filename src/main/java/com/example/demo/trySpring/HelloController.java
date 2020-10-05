package com.example.demo.trySpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HelloController {

	@Autowired
	private HelloService helloService;
	
	
	
	@GetMapping("/hello") 
	public String getHello(){
		return "hello";
	}
	
	@PostMapping("/hello")
	public String PostRequest(@RequestParam("text1")String str, Model model) {
		model.addAttribute("sample", str);
		return "HelloResponse";
	}
	
	@PostMapping("/hello/db")
	public String PostDbRequest(@RequestParam("text2")String str, Model model) {
		int id = Integer.parseInt(str);
		
		//１件検索
		Employee employee = helloService.findOne(id);
				
		//検索結果をモデルに登録
		model.addAttribute("id", employee.getEmployeeId());
		model.addAttribute("name", employee.getEmployeeName());
		model.addAttribute("age", employee.getAge());
		
		return "helloResponseDB";
	}
	
}
