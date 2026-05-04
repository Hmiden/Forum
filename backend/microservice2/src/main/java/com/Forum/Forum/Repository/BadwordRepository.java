package com.Forum.Forum.Repository;

import com.Forum.Forum.entity.Badword;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BadwordRepository extends JpaRepository<Badword, Long> {
    Optional<Badword> findByWordIgnoreCase(String word);
}
