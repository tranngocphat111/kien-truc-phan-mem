package iuh.fit.monolith.repository;


import iuh.fit.monolith.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<FoodOrder, Long> {}
