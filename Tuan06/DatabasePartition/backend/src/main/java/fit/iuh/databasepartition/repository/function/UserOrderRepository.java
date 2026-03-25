package fit.iuh.databasepartition.repository.function;

import fit.iuh.databasepartition.entity.function.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    List<UserOrder> findByUserId(Long userId);
    List<UserOrder> findByStatus(String status);
}
