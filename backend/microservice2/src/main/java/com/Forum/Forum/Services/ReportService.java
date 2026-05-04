package com.Forum.Forum.Services;

import com.Forum.Forum.Repository.IReplyRepository;
import com.Forum.Forum.Repository.ReportRepository;
import com.Forum.Forum.entity.Reply;
import com.Forum.Forum.entity.Report;
import com.Forum.Forum.entity.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final IReplyRepository replyRepository;

    public Report createReport(Long replyId, String reason, String reportedBy) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        Report report = new Report();
        report.setReply(reply);
        report.setReason(reason);
        report.setReportedBy(reportedBy);

        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> getReportsByStatus(ReportStatus status) {
        return reportRepository.findByStatus(status);
    }

    public Report updateReportStatus(Long id, ReportStatus status) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(status);
        return reportRepository.save(report);
    }
}
