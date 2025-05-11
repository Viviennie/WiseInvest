package com.plugin.vivien.account.domain.vo;

import com.plugin.vivien.account.domain.TradingAccount;
import lombok.Data;

@Data
public class BankcardVO {
    private Long tradingAccount;
    private String bankcardNumber;

    public BankcardVO(TradingAccount tradingAccount) {
        this.tradingAccount = tradingAccount.getTradingAccountId();
        this.bankcardNumber = tradingAccount.getBankcardNumber();
    }
}
