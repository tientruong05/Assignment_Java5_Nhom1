package poly.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.entity.OrderEntity;
import poly.edu.dao.OrderDAO;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public Optional<OrderEntity> getOrderById(int id) {
        return orderDAO.findById(id);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    public void addOrder(OrderEntity order) {
        orderDAO.save(order);
    }

    @Override
    public void updateOrder(OrderEntity order) {
        orderDAO.update(order);
    }

    @Override
    public void removeOrder(int id) {
        orderDAO.delete(id);
    }
}

