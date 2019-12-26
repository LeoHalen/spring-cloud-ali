package site.zhigang.pocketcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.zhigang.pocketcloud.annotation.ResponseResult;

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
