package com.seme.wiseinvest.recommendation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seme.wiseinvest.recommendation.service.RecommendationService;
import com.seme.wiseinvest.product.domain.Product;
import com.seme.wiseinvest.common.domain.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 推荐控制器的集成测试类
 * 测试 RecommendationController 是否能正确响应个性化推荐请求
 */
@WebMvcTest(controllers = RecommendationController.class)
public class RecommendationControllerIntegrationTest {

    // 用于模拟 HTTP 请求
    @Autowired
    private MockMvc mockMvc;

    // 使用 MockBean 模拟推荐服务，避免实际调用业务逻辑
    @MockBean
    private RecommendationService recommendationService;

    // JSON 序列化工具，可用于手动序列化/反序列化数据
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 测试接口：GET /recommendations
     * 用例说明：模拟用户请求推荐接口，验证是否返回状态 200，且响应格式为 code=0 且 data 为数组
     */
    @Test
    public void testGetMyRecommendations() throws Exception {
        Long fundAccount = 12345L;

        // 模拟服务层返回空推荐列表
        when(recommendationService.getPersonalizedRecommendations(fundAccount))
                .thenReturn(Collections.emptyList());

        // 发起 GET 请求并断言响应结构和状态码
        mockMvc.perform(get("/recommendations")
                        .header("X-Fund-Account", fundAccount))
                .andExpect(status().isOk()) // 状态码 200
                .andExpect(jsonPath("$.code").value(0)) // 成功码
                .andExpect(jsonPath("$.data").isArray()); // 返回数据应为数组
    }
}
