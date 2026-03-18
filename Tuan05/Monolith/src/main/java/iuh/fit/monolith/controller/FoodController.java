package iuh.fit.monolith.controller;

import iuh.fit.monolith.entity.FoodItem;
import iuh.fit.monolith.entity.FoodOrder;
import iuh.fit.monolith.repository.FoodItemRepository;
import iuh.fit.monolith.repository.OrderRepository;
import iuh.fit.monolith.repository.RestaurantRepository;
import iuh.fit.monolith.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class FoodController {
    @Autowired
    private RestaurantRepository resRepo;
    @Autowired private FoodItemRepository foodRepo;
    @Autowired private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    // 1. Lấy danh sách món ăn
    @GetMapping("/foods")
    public List<FoodItem> getAllFoods() {
        return foodRepo.findAll();
    }

    // 2. Đặt hàng
    @PostMapping("/orders")
    public FoodOrder createOrder(@RequestParam String name, @RequestBody List<Long> foodIds) {
        return orderService.placeOrder(name, foodIds);
    }

    @GetMapping("/orders")
    public List<FoodOrder> getAllOrders() {
        return orderService.getAllOrder(); // Lấy tất cả đơn hàng đã lưu trong MariaDB
    }
}
