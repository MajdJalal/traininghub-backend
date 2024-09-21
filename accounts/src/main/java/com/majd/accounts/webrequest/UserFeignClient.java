package com.majd.accounts.webrequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-client", url = "http://localhost:7080/admin/realms/trainingHubV1/users")
public interface UserFeignClient {

    @GetMapping
    String getUsers(@RequestHeader("Authorization") String authorization);
}
