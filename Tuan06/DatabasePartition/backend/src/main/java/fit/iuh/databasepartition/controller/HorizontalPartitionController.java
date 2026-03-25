package fit.iuh.databasepartition.controller;

import fit.iuh.databasepartition.dto.HorizontalUserDTO;
import fit.iuh.databasepartition.service.HorizontalPartitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller cho HORIZONTAL PARTITION
 * Demo: Chia user theo giới tính vào 2 bảng khác nhau
 */
@RestController
@RequestMapping("/api/horizontal")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HorizontalPartitionController {

    private final HorizontalPartitionService service;

    /**
     * Tạo user mới - tự động chọn bảng dựa trên gender
     */
    @PostMapping("/users")
    public ResponseEntity<HorizontalUserDTO> createUser(@RequestBody HorizontalUserDTO dto) {
        return ResponseEntity.ok(service.createUser(dto));
    }

    /**
     * Lấy tất cả user (từ cả 2 bảng)
     */
    @GetMapping("/users")
    public ResponseEntity<List<HorizontalUserDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    /**
     * Lấy tất cả user NAM (từ bảng user_male)
     */
    @GetMapping("/users/male")
    public ResponseEntity<List<HorizontalUserDTO>> getAllMaleUsers() {
        return ResponseEntity.ok(service.getAllMaleUsers());
    }

    /**
     * Lấy tất cả user NỮ (từ bảng user_female)
     */
    @GetMapping("/users/female")
    public ResponseEntity<List<HorizontalUserDTO>> getAllFemaleUsers() {
        return ResponseEntity.ok(service.getAllFemaleUsers());
    }

    /**
     * Tìm user theo gender và ID
     */
    @GetMapping("/users/{gender}/{id}")
    public ResponseEntity<HorizontalUserDTO> findUser(
            @PathVariable String gender,
            @PathVariable Long id) {
        HorizontalUserDTO user = service.findByGenderAndId(gender, id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Xóa user theo gender và ID
     */
    @DeleteMapping("/users/{gender}/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String gender,
            @PathVariable Long id) {
        service.deleteUser(gender, id);
        return ResponseEntity.ok().build();
    }
}
