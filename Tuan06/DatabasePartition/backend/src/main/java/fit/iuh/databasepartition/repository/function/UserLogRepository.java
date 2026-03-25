package fit.iuh.databasepartition.repository.function;

import fit.iuh.databasepartition.entity.function.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
    List<UserLog> findByUserId(Long userId);
    List<UserLog> findByAction(String action);
}
