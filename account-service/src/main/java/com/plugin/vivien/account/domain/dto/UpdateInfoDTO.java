package com.plugin.vivien.account.domain.dto;

import lombok.Data;

@Data
public class UpdateInfoDTO {
    private String fundAccount;
    private int riskLevel;
    private String phoneNumber;
}
