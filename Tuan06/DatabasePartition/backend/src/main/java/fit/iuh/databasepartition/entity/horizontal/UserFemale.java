package fit.iuh.databasepartition.entity.horizontal;

import jakarta.persistence.*;
import lombok.*;

/**
 * HORIZONTAL PARTITION - Bảng user NỮ
 * Dữ liệu được chia theo ROW dựa trên giới tính
 */
@Entity
@Table(name = "user_female")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFemale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private Integer age;

    // Luôn là "FEMALE"
    @Column(name = "gender")
    private String gender = "FEMALE";
}
