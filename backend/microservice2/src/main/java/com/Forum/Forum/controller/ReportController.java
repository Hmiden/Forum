package com.Forum.Forum.controller;

import com.Forum.Forum.Services.ReportService;
import com.Forum.Forum.entity.Report;
import com.Forum.Forum.entity.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // Available to USER and ADMIN (authenticated)
    @PostMapping("/reply/{replyId}")
    public org.springframework.http.ResponseEntity<?> createReport(@PathVariable Long replyId, @RequestBody Map<String, String> payload) {
        try {
            String reason = payload.get("reason");
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String reportedBy = authentication != null ? authentication.getName() : "Anonymous";

            Report report = reportService.createReport(replyId, reason, reportedBy);
            return org.springframework.http.ResponseEntity.ok(report);
        } catch (Exception e) {
            e.printStackTrace();
            return org.springframework.http.ResponseEntity.status(500).body("Error: " + e.getMessage() + " | Cause: " + (e.getCause() != null ? e.getCause().getMessage() : "none"));
        }
    }

    // ========== ADMIN ENDPOINTS ==========

    @GetMapping("/admin/all")
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @PatchMapping("/admin/{id}/status")
    public Report updateStatus(@PathVariable Long id, @RequestParam ReportStatus status) {
        return reportService.updateReportStatus(id, status);
    }
}
