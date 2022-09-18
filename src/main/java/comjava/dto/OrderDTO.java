package comjava.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    private Long id;

    private UserDTO user;

    private ProductDTO product;

    private double shippingFee;

    private LocalDateTime createdAt;
}
