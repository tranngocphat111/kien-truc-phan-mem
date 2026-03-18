package iuh.fit.monolith.service;

import iuh.fit.monolith.entity.FoodItem;
import iuh.fit.monolith.entity.FoodOrder;
import iuh.fit.monolith.repository.FoodItemRepository;
import iuh.fit.monolith.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired private FoodItemRepository foodItemRepository;

    public FoodOrder placeOrder(String customerName, List<Long> foodIds) {
        List<FoodItem> items = foodItemRepository.findAllById(foodIds);
        double total = items.stream().mapToDouble(FoodItem::getPrice).sum();

        FoodOrder order = new FoodOrder();
        order.setCustomerName(customerName);
        order.setItems(items);
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    public List<FoodOrder> getAllOrder(){
        return orderRepository.findAll();
    }
}
