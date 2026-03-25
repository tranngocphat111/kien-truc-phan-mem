package fit.iuh.restaurantservice.controller;

import fit.iuh.restaurantservice.entity.MenuItem;
import fit.iuh.restaurantservice.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public List<MenuItem> getAll() {
        return menuService.findAll();
    }

    @GetMapping("/available")
    public List<MenuItem> getAvailable() {
        return menuService.findAvailable();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getById(@PathVariable Long id) {
        return menuService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> getByCategory(@PathVariable String category) {
        return menuService.findByCategory(category);
    }

    @PostMapping
    public MenuItem create(@RequestBody MenuItem menuItem) {
        return menuService.save(menuItem);
    }

    @PutMapping("/{id}")
    public MenuItem update(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        menuItem.setId(id);
        return menuService.save(menuItem);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        menuService.deleteById(id);
    }
}
