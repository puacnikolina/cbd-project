package org.example.bookingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceProxy {

    @GetMapping("/users/{id}/exists")
    boolean userExists(@PathVariable Integer id);
}
