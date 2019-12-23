package site.zhigang.pocketcloud.auth.common.service;

import site.zhigang.pocketcloud.auth.common.bean.AccountInfo;

/**
 * Create by Zg.Li · 2019/12/11
 */
public interface AccountService {
    AccountInfo getAccountInfo(String token);
    AccountInfo getAccountInfoById(int accountId);
}
