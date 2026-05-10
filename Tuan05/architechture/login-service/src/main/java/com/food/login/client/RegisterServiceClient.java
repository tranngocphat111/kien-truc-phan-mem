package com.food.login.client;

import com.food.login.dto.UserLookupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "register-service", url = "${services.register.url}")
public interface RegisterServiceClient {

    @GetMapping("/api/users/{username}")
    UserLookupResponse getUserByUsername(@PathVariable("username") String username);
}
