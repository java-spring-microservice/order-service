package comjava.controller;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import comjava.dto.OrderDTO;
import comjava.entity.Order;
import comjava.repository.OrderRepository;
import comjava.service.ProductServiceProxy;
import comjava.service.UserServiceProxy;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceProxy productServiceProxy;

    @Autowired
    private UserServiceProxy userServiceProxy;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Order> getOrderList() {
        return orderRepository.findAll();
    }

    @GetMapping("/{orderId}")
    @Retry(name = "default", fallbackMethod = "getOrderDefault")
    @CircuitBreaker(name = "default", fallbackMethod = "getOrderDefault")
    @RateLimiter(name = "default", fallbackMethod = "getOrderDefault")
    @Bulkhead(name = "default", fallbackMethod = "getOrderDefault")
    public OrderDTO getOrderDetail(@PathVariable Long orderId) {
        logger.info("IN - orderId: {}", orderId);
        Order order = orderRepository.findById(orderId).orElse(null);

        OrderDTO result = modelMapper.map(order, OrderDTO.class);

        result.setProduct(productServiceProxy.getProductDetail(order.getProductId()));
        result.setUser(userServiceProxy.getUserDetail(order.getUserId()));

        return result;
    }

    private OrderDTO getOrderDefault(Exception e) {
        logger.error(e.getMessage());
        OrderDTO result = new OrderDTO();
        result.setId(0L);
        return result;
    }
}
