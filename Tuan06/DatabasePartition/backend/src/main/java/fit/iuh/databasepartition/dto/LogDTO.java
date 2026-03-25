package fit.iuh.databasepartition.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO cho Log trong Function Partition
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDTO {
    private Long id;
    private Long userId;
    private String action;
    private String description;
    private LocalDateTime logTime;
    private String ipAddress;
}
