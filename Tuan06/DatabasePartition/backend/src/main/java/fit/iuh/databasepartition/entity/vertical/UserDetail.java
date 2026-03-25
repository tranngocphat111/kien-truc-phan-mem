package fit.iuh.databasepartition.entity.vertical;

import jakarta.persistence.*;
import lombok.*;

/**
 * VERTICAL PARTITION - Thông tin CHI TIẾT của user
 * Dữ liệu được chia theo COLUMN
 * Bảng này chứa các cột ít truy cập hơn
 */
@Entity
@Table(name = "user_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key đến user_basic
    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String address;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private Integer age;
}
