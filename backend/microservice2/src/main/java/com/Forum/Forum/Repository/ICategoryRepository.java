package com.Forum.Forum.Repository;

import com.Forum.Forum.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
