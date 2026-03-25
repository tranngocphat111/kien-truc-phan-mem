package fit.iuh.databasepartition.controller;

import fit.iuh.databasepartition.dto.VerticalUserDTO;
import fit.iuh.databasepartition.service.VerticalPartitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller cho VERTICAL PARTITION
 * Demo: Chia thông tin user theo cột (basic & detail)
 */
@RestController
@RequestMapping("/api/vertical")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VerticalPartitionController {

    private final VerticalPartitionService service;

    /**
     * Tạo user mới - lưu vào 2 bảng (basic & detail)
     */
    @PostMapping("/users")
    public ResponseEntity<VerticalUserDTO> createUser(@RequestBody VerticalUserDTO dto) {
        return ResponseEntity.ok(service.createUser(dto));
    }

    /**
     * Lấy CHỈ thông tin cơ bản (query nhanh từ 1 bảng)
     */
    @GetMapping("/users/basic")
    public ResponseEntity<List<VerticalUserDTO>> getAllBasicInfo() {
        return ResponseEntity.ok(service.getAllBasicInfo());
    }

    /**
     * Lấy thông tin ĐẦY ĐỦ (join 2 bảng)
     */
    @GetMapping("/users/full")
    public ResponseEntity<List<VerticalUserDTO>> getAllFullInfo() {
        return ResponseEntity.ok(service.getAllFullInfo());
    }

    /**
     * Lấy thông tin đầy đủ của 1 user
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<VerticalUserDTO> getFullInfo(@PathVariable Long id) {
        VerticalUserDTO user = service.getFullInfo(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Cập nhật CHỈ thông tin chi tiết
     */
    @PutMapping("/users/{id}/detail")
    public ResponseEntity<VerticalUserDTO> updateDetail(
            @PathVariable Long id,
            @RequestBody VerticalUserDTO dto) {
        return ResponseEntity.ok(service.updateDetail(id, dto));
    }

    /**
     * Xóa user (cả 2 bảng)
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
