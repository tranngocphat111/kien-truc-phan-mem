package fit.iuh.databasepartition.service;

import fit.iuh.databasepartition.dto.VerticalUserDTO;
import fit.iuh.databasepartition.entity.vertical.UserBasic;
import fit.iuh.databasepartition.entity.vertical.UserDetail;
import fit.iuh.databasepartition.repository.vertical.UserBasicRepository;
import fit.iuh.databasepartition.repository.vertical.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * VERTICAL PARTITION SERVICE
 *
 * Logic: Chia dữ liệu theo CỘT (Column)
 * - user_basic: Thông tin cơ bản (id, name, email) - truy cập thường xuyên
 * - user_detail: Thông tin chi tiết (address, phone, bio) - ít truy cập hơn
 *
 * Ưu điểm:
 * - Query nhanh hơn khi chỉ cần thông tin cơ bản
 * - Giảm I/O khi không cần load toàn bộ dữ liệu
 * - Dễ cache thông tin cơ bản
 */
@Service
@RequiredArgsConstructor
public class VerticalPartitionService {

    private final UserBasicRepository userBasicRepository;
    private final UserDetailRepository userDetailRepository;

    /**
     * Tạo user - lưu vào 2 bảng riêng biệt
     */
    @Transactional
    public VerticalUserDTO createUser(VerticalUserDTO dto) {
        // Lưu thông tin cơ bản vào user_basic
        UserBasic basic = UserBasic.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .build();
        basic = userBasicRepository.save(basic);

        // Lưu thông tin chi tiết vào user_detail
        UserDetail detail = UserDetail.builder()
                .userId(basic.getId())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .bio(dto.getBio())
                .age(dto.getAge())
                .build();
        userDetailRepository.save(detail);

        dto.setId(basic.getId());
        return dto;
    }

    /**
     * Lấy CHỈ thông tin cơ bản (query nhanh)
     */
    public List<VerticalUserDTO> getAllBasicInfo() {
        return userBasicRepository.findAll().stream()
                .map(u -> VerticalUserDTO.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .email(u.getEmail())
                        .gender(u.getGender())
                        .build())
                .toList();
    }

    /**
     * Lấy thông tin ĐẦY ĐỦ (join 2 bảng)
     */
    public VerticalUserDTO getFullInfo(Long userId) {
        UserBasic basic = userBasicRepository.findById(userId).orElse(null);
        if (basic == null) return null;

        UserDetail detail = userDetailRepository.findByUserId(userId).orElse(null);

        return VerticalUserDTO.builder()
                .id(basic.getId())
                .name(basic.getName())
                .email(basic.getEmail())
                .gender(basic.getGender())
                .address(detail != null ? detail.getAddress() : null)
                .phone(detail != null ? detail.getPhone() : null)
                .bio(detail != null ? detail.getBio() : null)
                .age(detail != null ? detail.getAge() : null)
                .build();
    }

    /**
     * Lấy tất cả user với thông tin đầy đủ
     */
    public List<VerticalUserDTO> getAllFullInfo() {
        return userBasicRepository.findAll().stream()
                .map(basic -> {
                    UserDetail detail = userDetailRepository.findByUserId(basic.getId()).orElse(null);
                    return VerticalUserDTO.builder()
                            .id(basic.getId())
                            .name(basic.getName())
                            .email(basic.getEmail())
                            .gender(basic.getGender())
                            .address(detail != null ? detail.getAddress() : null)
                            .phone(detail != null ? detail.getPhone() : null)
                            .bio(detail != null ? detail.getBio() : null)
                            .age(detail != null ? detail.getAge() : null)
                            .build();
                })
                .toList();
    }

    /**
     * Cập nhật CHỈ thông tin chi tiết
     */
    @Transactional
    public VerticalUserDTO updateDetail(Long userId, VerticalUserDTO dto) {
        UserDetail detail = userDetailRepository.findByUserId(userId).orElse(null);
        if (detail != null) {
            detail.setAddress(dto.getAddress());
            detail.setPhone(dto.getPhone());
            detail.setBio(dto.getBio());
            detail.setAge(dto.getAge());
            userDetailRepository.save(detail);
        }
        return getFullInfo(userId);
    }

    /**
     * Xóa user (cả 2 bảng)
     */
    @Transactional
    public void deleteUser(Long userId) {
        userDetailRepository.findByUserId(userId).ifPresent(userDetailRepository::delete);
        userBasicRepository.deleteById(userId);
    }
}
