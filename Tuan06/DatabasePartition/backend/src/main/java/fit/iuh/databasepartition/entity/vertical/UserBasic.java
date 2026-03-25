package fit.iuh.databasepartition.entity.vertical;

import jakarta.persistence.*;
import lombok.*;

/**
 * VERTICAL PARTITION - Thông tin CƠ BẢN của user
 * Dữ liệu được chia theo COLUMN
 * Bảng này chứa các cột thường xuyên truy cập
 */
@Entity
@Table(name = "user_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String gender;
}
