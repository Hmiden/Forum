package com.Forum.Forum.Repository;

import com.Forum.Forum.entity.Report;
import com.Forum.Forum.entity.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStatus(ReportStatus status);
}
