package com.pocketcloud.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pocketcloud.response.common.annotation.ResponseResult;

/**
 * @Author Zg.Li · 2019/12/25
 */
@RestController
public class AuthController {

	@GetMapping("beforeLogin")
	@ResponseResult
	public Object beforeLogin() {
		return null;
	}
}
