package com.seme.wiseinvest.transaction.controller;

import com.seme.wiseinvest.common.domain.Result;
import com.seme.wiseinvest.transaction.domain.dto.HoldingDTO;
import com.seme.wiseinvest.transaction.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {
    @Autowired
    private QueryService queryService;

    @GetMapping("/transactions")
    public Result getTransactions(String fundAccount) {
        return Result.success(queryService.getTransactions(Long.parseLong(fundAccount)));
    }

    @GetMapping("/holdings")
    public Result getHoldingsByFundAccount(@RequestParam Long fundAccount) {
        List<HoldingDTO> holdings = queryService.getHoldingsByFundAccount(fundAccount);
        return Result.success(holdings); // Result.success(Object data)
    }
}
