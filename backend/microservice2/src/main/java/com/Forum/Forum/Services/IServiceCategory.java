package com.Forum.Forum.Services;



import com.Forum.Forum.entity.Category;

import java.util.List;

public interface IServiceCategory {
    Category create(Category category);
    List<Category> getAll();
    Category getById(Long id);
    Category update(Long id, Category category);
    void delete(Long id);
}
