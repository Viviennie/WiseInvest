package com.plugin.vivien.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plugin.vivien.account.domain.Admin;
import com.plugin.vivien.account.domain.User;

public interface AdminService extends IService<Admin> {
    boolean createAdmin(Admin admin);
}
