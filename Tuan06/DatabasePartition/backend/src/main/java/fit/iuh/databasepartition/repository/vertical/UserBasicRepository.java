package fit.iuh.databasepartition.repository.vertical;

import fit.iuh.databasepartition.entity.vertical.UserBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBasicRepository extends JpaRepository<UserBasic, Long> {
    Optional<UserBasic> findByEmail(String email);
}
