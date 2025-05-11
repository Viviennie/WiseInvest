package com.plugin.vivien.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plugin.vivien.account.domain.Customer;
import com.plugin.vivien.account.domain.dto.CustomerDTO;
import com.plugin.vivien.account.domain.dto.UpdateInfoDTO;
import com.plugin.vivien.account.domain.vo.CustomerVO;

import java.util.List;

public interface CustomerService extends IService<Customer> {
    Long createAccount(CustomerDTO customerDTO);

    boolean updateRiskLevel(Long fundAccount, int riskLevel);

    boolean updateInfo(UpdateInfoDTO updateInfoDTO);

    List<CustomerVO> getCustomers(int pageNum, int pageSize, String key);

}