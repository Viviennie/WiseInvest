package com.seme.wiseinvest.recommendation.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seme.wiseinvest.account.domain.Customer;
import com.seme.wiseinvest.api.client.AccountClient;
import com.seme.wiseinvest.api.client.ProductClient;
import com.seme.wiseinvest.api.client.TransactionClient;
import com.seme.wiseinvest.common.domain.Result;
import com.seme.wiseinvest.product.domain.Product;
import com.seme.wiseinvest.transaction.domain.dto.HoldingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * RecommendationServiceImpl 的单元测试类
 * 用于验证推荐逻辑在不同输入条件下的正确性
 */
public class RecommendationServiceImplTest {

    private RecommendationServiceImpl recommendationService;
    private AccountClient accountClient;
    private ProductClient productClient;
    private TransactionClient transactionClient;

    /**
     * 测试初始化方法
     * 使用 Mockito 创建 mock 对象，注入到 RecommendationServiceImpl 实例中
     */
    @BeforeEach
    public void setup() {
        accountClient = mock(AccountClient.class);
        productClient = mock(ProductClient.class);
        transactionClient = mock(TransactionClient.class);
        recommendationService = new RecommendationServiceImpl(accountClient, productClient, transactionClient);
    }

    /**
     * 测试场景：给定有效的 fundAccount，且用户风险等级、持仓、产品列表等数据均合法
     * 预期行为：返回一条推荐产品（符合条件、未持有、风险等级匹配）
     */
    @Test
    public void testGetPersonalizedRecommendations_withValidAccount() {
        Long fundAccount = 10001L;

        // 模拟用户信息（风险等级为 3）
        Customer customer = new Customer();
        customer.setRiskLevel(3);
        Result customerResult = Result.success(customer);
        when(accountClient.getCustomerByFundAccount(fundAccount)).thenReturn(customerResult);

        // 模拟持仓信息（持有 productId=101）
        List<HoldingDTO> holdings = Arrays.asList(new HoldingDTO(101, "acc-1"));
        Result holdingResult = Result.success(holdings);
        when(transactionClient.getHoldingsByFundAccount(fundAccount)).thenReturn(holdingResult);

        // 模拟产品信息（提供一个符合推荐条件的产品 productId=102）
        Product product1 = new Product();
        product1.setProductId(102);
        product1.setRiskLevel(2);
        product1.setProductStatus(1); // 正常状态
        product1.setProductRating(4.5);
        product1.setProductAum(1000.0);
        Result productResult = Result.success(Arrays.asList(product1));
        when(productClient.getAllProducts()).thenReturn(productResult);

        // 执行推荐逻辑
        List<Product> recommended = recommendationService.getPersonalizedRecommendations(fundAccount);

        // 验证推荐结果
        assertNotNull(recommended); // 不为 null
        assertFalse(recommended.isEmpty()); // 不为空
        assertEquals(1, recommended.size()); // 正好一个推荐
        assertEquals(product1.getProductId(), recommended.get(0).getProductId()); // ID 匹配
    }

    /**
     * 测试场景：输入的 fundAccount 为 null
     * 预期行为：返回空的推荐结果列表
     */
    @Test
    public void testGetPersonalizedRecommendations_withNullFundAccount() {
        List<Product> result = recommendationService.getPersonalizedRecommendations(null);
        assertTrue(result.isEmpty());
    }
}
