package com.plugin.vivien.api.client;

import com.plugin.vivien.api.OurSystem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("settle-service")
public interface SettleClient {
    @PostMapping("/settle/system")
    OurSystem getSystem();
}