package fit.iuh.databasepartition.service;

import fit.iuh.databasepartition.dto.LogDTO;
import fit.iuh.databasepartition.dto.OrderDTO;
import fit.iuh.databasepartition.entity.function.UserLog;
import fit.iuh.databasepartition.entity.function.UserOrder;
import fit.iuh.databasepartition.repository.function.UserLogRepository;
import fit.iuh.databasepartition.repository.function.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * FUNCTION PARTITION SERVICE
 *
 * Logic: Chia dữ liệu theo CHỨC NĂNG
 * - user_order: Quản lý đơn hàng
 * - user_log: Ghi log hoạt động
 *
 * Ưu điểm:
 * - Mỗi bảng phục vụ một chức năng riêng biệt
 * - Dễ bảo trì và mở rộng từng chức năng
 * - Có thể đặt các bảng trên các server khác nhau
 * - Query nhanh hơn vì dữ liệu được phân tách theo chức năng
 */
@Service
@RequiredArgsConstructor
public class FunctionPartitionService {

    private final UserOrderRepository userOrderRepository;
    private final UserLogRepository userLogRepository;

    // ==================== ORDER FUNCTIONS ====================

    /**
     * Tạo đơn hàng mới
     */
    public OrderDTO createOrder(OrderDTO dto) {
        UserOrder order = UserOrder.builder()
                .userId(dto.getUserId())
                .productName(dto.getProductName())
                .amount(dto.getAmount())
                .orderDate(LocalDateTime.now())
                .status("PENDING")
                .build();
        order = userOrderRepository.save(order);

        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        return dto;
    }

    /**
     * Lấy tất cả đơn hàng
     */
    public List<OrderDTO> getAllOrders() {
        return userOrderRepository.findAll().stream()
                .map(o -> OrderDTO.builder()
                        .id(o.getId())
                        .userId(o.getUserId())
                        .productName(o.getProductName())
                        .amount(o.getAmount())
                        .orderDate(o.getOrderDate())
                        .status(o.getStatus())
                        .build())
                .toList();
    }

    /**
     * Lấy đơn hàng theo user
     */
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return userOrderRepository.findByUserId(userId).stream()
                .map(o -> OrderDTO.builder()
                        .id(o.getId())
                        .userId(o.getUserId())
                        .productName(o.getProductName())
                        .amount(o.getAmount())
                        .orderDate(o.getOrderDate())
                        .status(o.getStatus())
                        .build())
                .toList();
    }

    /**
     * Cập nhật trạng thái đơn hàng
     */
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        UserOrder order = userOrderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            order = userOrderRepository.save(order);
            return OrderDTO.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .productName(order.getProductName())
                    .amount(order.getAmount())
                    .orderDate(order.getOrderDate())
                    .status(order.getStatus())
                    .build();
        }
        return null;
    }

    // ==================== LOG FUNCTIONS ====================

    /**
     * Ghi log hoạt động
     */
    public LogDTO createLog(LogDTO dto) {
        UserLog log = UserLog.builder()
                .userId(dto.getUserId())
                .action(dto.getAction())
                .description(dto.getDescription())
                .logTime(LocalDateTime.now())
                .ipAddress(dto.getIpAddress())
                .build();
        log = userLogRepository.save(log);

        dto.setId(log.getId());
        dto.setLogTime(log.getLogTime());
        return dto;
    }

    /**
     * Lấy tất cả logs
     */
    public List<LogDTO> getAllLogs() {
        return userLogRepository.findAll().stream()
                .map(l -> LogDTO.builder()
                        .id(l.getId())
                        .userId(l.getUserId())
                        .action(l.getAction())
                        .description(l.getDescription())
                        .logTime(l.getLogTime())
                        .ipAddress(l.getIpAddress())
                        .build())
                .toList();
    }

    /**
     * Lấy logs theo user
     */
    public List<LogDTO> getLogsByUserId(Long userId) {
        return userLogRepository.findByUserId(userId).stream()
                .map(l -> LogDTO.builder()
                        .id(l.getId())
                        .userId(l.getUserId())
                        .action(l.getAction())
                        .description(l.getDescription())
                        .logTime(l.getLogTime())
                        .ipAddress(l.getIpAddress())
                        .build())
                .toList();
    }

    /**
     * Lấy logs theo action type
     */
    public List<LogDTO> getLogsByAction(String action) {
        return userLogRepository.findByAction(action).stream()
                .map(l -> LogDTO.builder()
                        .id(l.getId())
                        .userId(l.getUserId())
                        .action(l.getAction())
                        .description(l.getDescription())
                        .logTime(l.getLogTime())
                        .ipAddress(l.getIpAddress())
                        .build())
                .toList();
    }
}
