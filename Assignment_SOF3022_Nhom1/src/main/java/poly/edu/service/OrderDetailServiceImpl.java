package poly.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.entity.OrderDetailEntity;
import poly.edu.dao.OrderDetailDAO;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailDAO orderDetailDAO;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailDAO orderDetailDAO) {
        this.orderDetailDAO = orderDetailDAO;
    }

    @Override
    public Optional<OrderDetailEntity> getOrderDetailById(int id) {
        return orderDetailDAO.findById(id);
    }

    @Override
    public List<OrderDetailEntity> getAllOrderDetails() {
        return orderDetailDAO.findAll();
    }

    @Override
    public void addOrderDetail(OrderDetailEntity orderDetail) {
        orderDetailDAO.save(orderDetail);
    }

    @Override
    public void updateOrderDetail(OrderDetailEntity orderDetail) {
        orderDetailDAO.update(orderDetail);
    }

    @Override
    public void removeOrderDetail(int id) {
        orderDetailDAO.delete(id);
    }
}

