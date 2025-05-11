package com.plugin.vivien.account.service;

import com.plugin.vivien.account.domain.dto.LoginDTO;

public interface LoginService {
    String checkPassword(LoginDTO loginDTO);

    boolean isVaildPhoneNumber(String phoneNumber);

    boolean isVaildAdminPhoneNumber(String phoneNumber);

    boolean changePassword(LoginDTO loginDTO);
}

