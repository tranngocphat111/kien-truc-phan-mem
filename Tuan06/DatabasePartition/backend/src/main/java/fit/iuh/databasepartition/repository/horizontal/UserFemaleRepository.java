package fit.iuh.databasepartition.repository.horizontal;

import fit.iuh.databasepartition.entity.horizontal.UserFemale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFemaleRepository extends JpaRepository<UserFemale, Long> {
    Optional<UserFemale> findByEmail(String email);
}
