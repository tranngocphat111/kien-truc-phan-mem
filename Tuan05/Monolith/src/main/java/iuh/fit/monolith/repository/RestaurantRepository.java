package iuh.fit.monolith.repository;


import iuh.fit.monolith.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {}
