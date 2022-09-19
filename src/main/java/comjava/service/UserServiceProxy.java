package comjava.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import comjava.dto.UserDTO;

@FeignClient(name = "user-service")
// @RibbonClient(name = "product-service")
public interface UserServiceProxy {

    @GetMapping("/users/{userId}")
    UserDTO getUserDetail(@PathVariable("userId") Long userId);
}
