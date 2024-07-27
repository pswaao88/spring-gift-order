package gift.Controller;

import gift.Model.Category;
import gift.Service.CategoryService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/api/category")
    public ResponseEntity<List<Category>> getCategory(){
        return ResponseEntity.ok().body(categoryService.getAllCategory());
    }

    @GetMapping("/api/category/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "categoryId") Long categoryId){
        return ResponseEntity.ok().body(categoryService.getCategoryById(categoryId));
    }

    @PostMapping("/api/category")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        return ResponseEntity.ok().body(categoryService.addCategory(category));
    }

    @PutMapping("/api/category")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        return ResponseEntity.ok().body(categoryService.updateCategory(category));
    }

    @DeleteMapping("/api/category/{categoryId}")
    public ResponseEntity<Category> deleteCategory(@PathVariable(value = "categoryId") Long categoryId){
        return ResponseEntity.ok().body(categoryService.deleteCategory(categoryId));
    }
}
