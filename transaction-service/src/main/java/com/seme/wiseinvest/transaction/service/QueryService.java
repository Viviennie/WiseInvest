package com.seme.wiseinvest.transaction.service;

import com.seme.wiseinvest.transaction.domain.vo.TransactionVO;

import java.util.List;

public interface QueryService {
    List<TransactionVO> getTransactions(Long fundAccount);
}