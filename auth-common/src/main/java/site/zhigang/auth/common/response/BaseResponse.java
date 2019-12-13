package site.zhigang.auth.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Zg.Li · 2019/12/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
	private int status = 200;
	private String message;
}
