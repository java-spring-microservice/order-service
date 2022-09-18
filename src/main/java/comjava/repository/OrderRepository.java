package comjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import comjava.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
