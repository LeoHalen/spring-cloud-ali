package site.zhigang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by Zg.Li · 2019/12/10
 */
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @RestController
    @RequestMapping("/sentinel")
    static class TestController{

        @GetMapping("/hello")
        public String hello() {
            return "hello sentinel!";
        }
    }
}
