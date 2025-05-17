package com.seme.wiseinvest.transaction.controller;

import com.seme.wiseinvest.common.domain.Result;
import com.seme.wiseinvest.transaction.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/query")
public class QueryController {
    @Autowired
    private QueryService queryService;

    @GetMapping("/transactions")
    public Result getTransactions(String fundAccount) {
        return Result.success(queryService.getTransactions(Long.parseLong(fundAccount)));
    }
}
