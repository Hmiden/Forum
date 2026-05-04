import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Reply } from 'src/app/models/reply';
import { ReplyDto } from 'src/app/models/reply-dto';
import { ReplyService } from 'src/app/services/reply.service';
import { ReportService } from 'src/app/services/report.service';
import { AuthService } from 'src/app/services/auth.service';
declare var bootstrap: any;

@Component({
  selector: 'app-reply-list',
  templateUrl: './reply-list.component.html',
  styleUrls: ['./reply-list.component.css']
})
export class ReplyListComponent implements OnInit {
  replies: ReplyDto[] = [];
  showForm = false;
  currentReply: Reply = { content: '', topicId: 0 };
  topicId!: number;
  selectedReplyId: number | null = null;

  // ✅ Report variables
  reportReplyId: number | null = null;
  reportReason: string = 'INAPPROPRIATE';
  reportSuccess: boolean = false;

  // ✅ Bad word error
  badWordError: string = '';

  constructor(
    private replyService: ReplyService,
    private reportService: ReportService,
    private route: ActivatedRoute,
    private location: Location,
    public authService: AuthService
  ) {}

  ngOnInit() {
    this.topicId = Number(this.route.snapshot.paramMap.get('topicId'));
    this.currentReply.topicId = this.topicId;
    this.loadReplies();
  }

  loadReplies() {
    this.replyService.getByTopic(this.topicId)
      .subscribe(data => this.replies = data);
  }

  handleSave(reply: Reply) {
    this.badWordError = '';
    if (reply.id) {
      this.replyService.update(reply.id, reply).subscribe({
        next: () => this.afterSave(),
        error: (err) => this.handleError(err)
      });
    } else {
      this.replyService.create(reply, this.topicId).subscribe({
        next: () => this.afterSave(),
        error: (err) => this.handleError(err)
      });
    }
  }

  // ✅ Handle bad word error from Spring Boot
  handleError(err: any) {
    if (err.error && err.error.message && err.error.message.includes('INAPPROPRIATE_CONTENT')) {
      this.badWordError = '❌ Your reply contains inappropriate words. Please edit your message.';
    } else {
      this.badWordError = '❌ An error occurred. Please try again.';
    }
  }

  editReply(replyDto: ReplyDto) {
    this.currentReply = {
      id: replyDto.id,
      content: replyDto.content,
      topicId: replyDto.topicId
    };
    this.showForm = true;
    this.badWordError = '';
  }

  cancelForm() {
    this.showForm = false;
    this.badWordError = '';
    this.currentReply = { content: '', topicId: this.topicId };
  }

  // ✅ Open report modal
  openReportModal(replyId: number) {
    this.reportReplyId = replyId;
    this.reportReason = 'INAPPROPRIATE';
    this.reportSuccess = false;
    const modal = new bootstrap.Modal(document.getElementById('reportModal')!);
    modal.show();
  }

  // ✅ Submit report
  submitReport() {
    if (this.reportReplyId !== null) {
      this.reportService.create(this.reportReplyId, this.reportReason).subscribe({
        next: () => {
          this.reportSuccess = true;
          setTimeout(() => {
            const modalEl = document.getElementById('reportModal')!;
            const modal = bootstrap.Modal.getInstance(modalEl);
            modal?.hide();
          }, 1500);
        },
        error: (err) => {
          console.error('Report error:', err);
          const msg = err.error?.message || err.error || 'Error submitting report.';
          alert(msg);
        }
      });
    }
  }

  deleteReply(id: number) {
    this.selectedReplyId = id;
    const modal = new bootstrap.Modal(document.getElementById('deleteReplyModal')!);
    modal.show();
  }

  confirmDelete() {
    if (this.selectedReplyId !== null) {
      this.replyService.delete(this.selectedReplyId).subscribe(() => {
        this.loadReplies();
        this.selectedReplyId = null;
        const modalEl = document.getElementById('deleteReplyModal')!;
        bootstrap.Modal.getInstance(modalEl)?.hide();
      });
    }
  }

  goBack() {
    this.location.back();
  }

  private afterSave() {
    this.loadReplies();
    this.cancelForm();
  }
}