package com.plugin.vivien.account.service;

import com.plugin.vivien.api.Bankcard;
import com.plugin.vivien.account.domain.dto.BankcardDTO;
import com.plugin.vivien.account.domain.vo.BankcardVO;

import java.util.List;

public interface TradingAccountService {
    String addBankcard(BankcardDTO bankcardDTO);

    boolean deleteBankcard(long tradingAccountId);

    List<BankcardVO> getBankcards(long fundAccount);

    Bankcard getBankcardByTradingAccountId(long tradingAccountId);

    boolean updateBalance(Bankcard bankcard);

    double getBalance(String bankcardNumber);

    List<String> getTradingAccounts(Long fundAccount);
}
