package com.example.demo.login.domain.model;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SignupForm {
	@NotBlank
	private String userId;
	private String password;
	private String userName;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date birthday;

	private int age;
	private boolean marriage;

}
