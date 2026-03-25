package fit.iuh.databasepartition.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO cho Order trong Function Partition
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Long userId;
    private String productName;
    private Double amount;
    private LocalDateTime orderDate;
    private String status;
}
