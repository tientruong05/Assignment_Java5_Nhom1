package poly.edu.service;

import poly.edu.entity.OrderEntity;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderEntity> getOrderById(int id);
    List<OrderEntity> getAllOrders();
    void addOrder(OrderEntity order);
    void updateOrder(OrderEntity order);
    void removeOrder(int id);
}

