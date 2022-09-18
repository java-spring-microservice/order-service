package comjava.controller;

import java.util.List;
import org.modelmapper.ModelMapper;
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

@RestController
@RequestMapping("/orders")
public class OrderController {

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
    public OrderDTO getOrderDetail(@PathVariable Long orderId) {

        Order order = orderRepository.findById(orderId).orElse(null);

        OrderDTO result = modelMapper.map(order, OrderDTO.class);

        result.setProduct(productServiceProxy.getProductDetail(order.getProductId()));
        result.setUser(userServiceProxy.getUserDetail(order.getUserId()));

        return result;
    }
}
