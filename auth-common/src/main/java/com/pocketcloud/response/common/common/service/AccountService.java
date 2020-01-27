package com.pocketcloud.response.common.common.service;

import com.pocketcloud.response.common.common.bean.AccountInfo;

/**
 * @Author Zg.Li · 2019/12/11
 */
public interface AccountService {
    AccountInfo getAccountInfo(String token);
    AccountInfo getAccountInfoById(int accountId);
}
