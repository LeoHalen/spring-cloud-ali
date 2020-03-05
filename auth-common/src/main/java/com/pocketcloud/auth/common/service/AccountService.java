package com.pocketcloud.auth.common.service;

import com.pocketcloud.auth.common.bean.AccountInfo;

/**
 * @Author Zg.Li · 2019/12/11
 */
public interface AccountService {
    AccountInfo getAccountInfo(String token);
    AccountInfo getAccountInfoById(int accountId);
}
