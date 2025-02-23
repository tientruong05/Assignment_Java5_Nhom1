package poly.edu.service;

import poly.edu.entity.OrderDetailEntity;
import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    Optional<OrderDetailEntity> getOrderDetailById(int id);
    List<OrderDetailEntity> getAllOrderDetails();
    void addOrderDetail(OrderDetailEntity orderDetail);
    void updateOrderDetail(OrderDetailEntity orderDetail);
    void removeOrderDetail(int id);
}

