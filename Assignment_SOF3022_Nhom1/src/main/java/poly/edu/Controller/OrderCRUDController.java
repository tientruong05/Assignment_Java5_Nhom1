package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.entity.OrderEntity;
import poly.edu.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderCRUDController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public String listOrders(Model model) {
        List<OrderEntity> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "crud_orders";
    }

    @GetMapping("/view/{id}")
    @ResponseBody
    public OrderEntity viewOrder(@PathVariable("id") int id) {
        return orderService.getOrderById(id).orElse(null);
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderEntity order) {
        orderService.addOrder(order);
        return "redirect:/admin/orders";
    }

    @PostMapping("/edit/{id}")
    public String editOrder(@PathVariable("id") int id, @ModelAttribute OrderEntity order) {
        order.setId(id);
        orderService.updateOrder(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id) {
        orderService.removeOrder(id);
        return "redirect:/admin/orders";
    }
}
