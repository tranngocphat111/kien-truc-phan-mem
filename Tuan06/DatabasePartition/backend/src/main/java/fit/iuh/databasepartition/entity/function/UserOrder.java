package fit.iuh.databasepartition.entity.function;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * FUNCTION PARTITION - Đơn hàng của user
 * Dữ liệu được chia theo CHỨC NĂNG
 * Bảng này phục vụ riêng cho chức năng quản lý đơn hàng
 */
@Entity
@Table(name = "user_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    private Double amount;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    private String status; // PENDING, COMPLETED, CANCELLED
}
