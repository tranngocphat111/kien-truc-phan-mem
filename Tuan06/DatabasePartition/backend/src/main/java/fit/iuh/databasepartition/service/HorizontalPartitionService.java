package fit.iuh.databasepartition.service;

import fit.iuh.databasepartition.dto.HorizontalUserDTO;
import fit.iuh.databasepartition.entity.horizontal.UserFemale;
import fit.iuh.databasepartition.entity.horizontal.UserMale;
import fit.iuh.databasepartition.repository.horizontal.UserFemaleRepository;
import fit.iuh.databasepartition.repository.horizontal.UserMaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * HORIZONTAL PARTITION SERVICE
 *
 * Logic: Dựa vào giới tính (gender) để quyết định lưu vào bảng nào
 * - MALE -> user_male
 * - FEMALE -> user_female
 *
 * Ưu điểm:
 * - Giảm kích thước bảng
 * - Query nhanh hơn khi chỉ cần query 1 bảng
 * - Dễ scale theo từng partition
 */
@Service
@RequiredArgsConstructor
public class HorizontalPartitionService {

    private final UserMaleRepository userMaleRepository;
    private final UserFemaleRepository userFemaleRepository;

    /**
     * Tạo user - tự động chọn bảng dựa trên giới tính
     */
    public HorizontalUserDTO createUser(HorizontalUserDTO dto) {
        if ("MALE".equalsIgnoreCase(dto.getGender())) {
            // Lưu vào bảng user_male
            UserMale user = UserMale.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .age(dto.getAge())
                    .gender("MALE")
                    .build();
            user = userMaleRepository.save(user);
            dto.setId(user.getId());
            return dto;
        } else {
            // Lưu vào bảng user_female
            UserFemale user = UserFemale.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .age(dto.getAge())
                    .gender("FEMALE")
                    .build();
            user = userFemaleRepository.save(user);
            dto.setId(user.getId());
            return dto;
        }
    }

    /**
     * Lấy tất cả user nam
     */
    public List<HorizontalUserDTO> getAllMaleUsers() {
        return userMaleRepository.findAll().stream()
                .map(u -> HorizontalUserDTO.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .email(u.getEmail())
                        .age(u.getAge())
                        .gender(u.getGender())
                        .build())
                .toList();
    }

    /**
     * Lấy tất cả user nữ
     */
    public List<HorizontalUserDTO> getAllFemaleUsers() {
        return userFemaleRepository.findAll().stream()
                .map(u -> HorizontalUserDTO.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .email(u.getEmail())
                        .age(u.getAge())
                        .gender(u.getGender())
                        .build())
                .toList();
    }

    /**
     * Lấy tất cả user (từ cả 2 bảng)
     */
    public List<HorizontalUserDTO> getAllUsers() {
        List<HorizontalUserDTO> result = new ArrayList<>();
        result.addAll(getAllMaleUsers());
        result.addAll(getAllFemaleUsers());
        return result;
    }

    /**
     * Tìm user theo giới tính và ID
     */
    public HorizontalUserDTO findByGenderAndId(String gender, Long id) {
        if ("MALE".equalsIgnoreCase(gender)) {
            return userMaleRepository.findById(id)
                    .map(u -> HorizontalUserDTO.builder()
                            .id(u.getId())
                            .name(u.getName())
                            .email(u.getEmail())
                            .age(u.getAge())
                            .gender(u.getGender())
                            .build())
                    .orElse(null);
        } else {
            return userFemaleRepository.findById(id)
                    .map(u -> HorizontalUserDTO.builder()
                            .id(u.getId())
                            .name(u.getName())
                            .email(u.getEmail())
                            .age(u.getAge())
                            .gender(u.getGender())
                            .build())
                    .orElse(null);
        }
    }

    /**
     * Xóa user theo giới tính và ID
     */
    public void deleteUser(String gender, Long id) {
        if ("MALE".equalsIgnoreCase(gender)) {
            userMaleRepository.deleteById(id);
        } else {
            userFemaleRepository.deleteById(id);
        }
    }
}
