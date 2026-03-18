package iuh.fit.monolith.repository;


import iuh.fit.monolith.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {}
