package site.zhigang.pocketcloud.auth.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author Zg.Li · 2019/12/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionInfo {
    /**
     * 模块名称
     */
    private String module;
    /**
     * 权限集
     */
    private Set<String> permissions;
}
