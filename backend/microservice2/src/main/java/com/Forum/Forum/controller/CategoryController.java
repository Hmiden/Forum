package com.Forum.Forum.controller;

import com.Forum.Forum.Services.IServiceCategory;
import com.Forum.Forum.entity.Category;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private IServiceCategory serviceCategory;

    @PostMapping
    public Category create(@RequestBody Category category) {
        return serviceCategory.create(category);
    }

    @GetMapping
    public List<Category> getAll() {
        return serviceCategory.getAll();
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return serviceCategory.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        serviceCategory.delete(id);
    }
}
