package fit.iuh.databasepartition.controller;

import fit.iuh.databasepartition.dto.LogDTO;
import fit.iuh.databasepartition.dto.OrderDTO;
import fit.iuh.databasepartition.service.FunctionPartitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller cho FUNCTION PARTITION
 * Demo: Chia dữ liệu theo chức năng (orders & logs)
 */
@RestController
@RequestMapping("/api/function")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FunctionPartitionController {

    private final FunctionPartitionService service;

    // ==================== ORDER ENDPOINTS ====================

    /**
     * Tạo đơn hàng mới
     */
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO dto) {
        return ResponseEntity.ok(service.createOrder(dto));
    }

    /**
     * Lấy tất cả đơn hàng
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    /**
     * Lấy đơn hàng theo userId
     */
    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getOrdersByUserId(userId));
    }

    /**
     * Cập nhật trạng thái đơn hàng
     */
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        OrderDTO order = service.updateOrderStatus(orderId, status);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    // ==================== LOG ENDPOINTS ====================

    /**
     * Ghi log hoạt động
     */
    @PostMapping("/logs")
    public ResponseEntity<LogDTO> createLog(@RequestBody LogDTO dto) {
        return ResponseEntity.ok(service.createLog(dto));
    }

    /**
     * Lấy tất cả logs
     */
    @GetMapping("/logs")
    public ResponseEntity<List<LogDTO>> getAllLogs() {
        return ResponseEntity.ok(service.getAllLogs());
    }

    /**
     * Lấy logs theo userId
     */
    @GetMapping("/logs/user/{userId}")
    public ResponseEntity<List<LogDTO>> getLogsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getLogsByUserId(userId));
    }

    /**
     * Lấy logs theo action type
     */
    @GetMapping("/logs/action/{action}")
    public ResponseEntity<List<LogDTO>> getLogsByAction(@PathVariable String action) {
        return ResponseEntity.ok(service.getLogsByAction(action));
    }
}
