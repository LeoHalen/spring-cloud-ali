package site.zhigang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Zg.Li · 2019/12/4
 */
@RestController
@RequestMapping("/nacos/config")
@RefreshScope
public class ConfigController {

	@Value("${localCache:false}")
	private boolean localCache;

	@GetMapping("/getCache")
	public boolean getCache() {
		return localCache;
	}
}
