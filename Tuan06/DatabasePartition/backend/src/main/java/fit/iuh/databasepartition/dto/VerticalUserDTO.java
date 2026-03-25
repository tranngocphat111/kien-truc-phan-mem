package fit.iuh.databasepartition.dto;

import lombok.*;

/**
 * DTO cho Vertical Partition
 * Kết hợp thông tin từ user_basic và user_detail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerticalUserDTO {
    // From user_basic
    private Long id;
    private String name;
    private String email;
    private String gender;

    // From user_detail
    private String address;
    private String phone;
    private String bio;
    private Integer age;
}
