package com.seme.wiseinvest.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Product {
    @TableId
    private Integer productId;
    private String productName;
    private String productType;
    private int riskLevel;
    private int productStatus;
}
