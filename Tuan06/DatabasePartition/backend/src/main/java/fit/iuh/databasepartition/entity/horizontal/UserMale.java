package fit.iuh.databasepartition.entity.horizontal;

import jakarta.persistence.*;
import lombok.*;

/**
 * HORIZONTAL PARTITION - Bảng user NAM
 * Dữ liệu được chia theo ROW dựa trên giới tính
 */
@Entity
@Table(name = "user_male")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private Integer age;

    // Luôn là "MALE"
    @Column(name = "gender")
    private String gender = "MALE";
}
