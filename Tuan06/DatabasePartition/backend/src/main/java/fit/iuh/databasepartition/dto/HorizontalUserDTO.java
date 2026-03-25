package fit.iuh.databasepartition.dto;

import lombok.*;

/**
 * DTO cho Horizontal Partition
 * Chứa thông tin user với giới tính để quyết định table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorizontalUserDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String gender; // MALE hoặc FEMALE -> quyết định table
}
