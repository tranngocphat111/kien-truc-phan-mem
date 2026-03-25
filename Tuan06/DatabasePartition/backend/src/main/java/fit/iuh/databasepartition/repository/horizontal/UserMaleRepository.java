package fit.iuh.databasepartition.repository.horizontal;

import fit.iuh.databasepartition.entity.horizontal.UserMale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMaleRepository extends JpaRepository<UserMale, Long> {
    Optional<UserMale> findByEmail(String email);
}
