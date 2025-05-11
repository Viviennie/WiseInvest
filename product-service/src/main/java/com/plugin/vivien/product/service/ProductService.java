package com.plugin.vivien.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plugin.vivien.api.NetValue;
import com.plugin.vivien.product.domain.Product;
import java.util.Date;
import java.util.List;



public interface ProductService extends IService<Product> {
    List<Product> list();
    List<Product> searchByKeyword(String keyword);

    Double getNetValueByProductIdAndDate(int productId, Date date);

    boolean saveProduct(Product product);

    void updateProduct(Product product);
    List<NetValue> getLatestNetValues();
    boolean insertNetValue(NetValue netValue);

}
