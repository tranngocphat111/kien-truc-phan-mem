package fit.iuh.databasepartition.entity.function;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * FUNCTION PARTITION - Log hoạt động của user
 * Dữ liệu được chia theo CHỨC NĂNG
 * Bảng này phục vụ riêng cho chức năng ghi log
 */
@Entity
@Table(name = "user_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String action; // LOGIN, LOGOUT, VIEW, PURCHASE

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "log_time")
    private LocalDateTime logTime;

    @Column(name = "ip_address")
    private String ipAddress;
}
