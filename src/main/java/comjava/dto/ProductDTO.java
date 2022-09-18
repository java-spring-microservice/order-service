package comjava.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Long id;

    private String name;

    private Double price;

    private Long quantity;
}
