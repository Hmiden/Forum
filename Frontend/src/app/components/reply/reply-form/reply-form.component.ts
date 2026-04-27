import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Reply } from 'src/app/models/reply';

@Component({
  selector: 'app-reply-form',
  templateUrl: './reply-form.component.html',
  styleUrls: ['./reply-form.component.css']
})
export class ReplyFormComponent {
@Input() reply: Reply = { content: '', topicId: 0 };
  @Input() titleForm: string = 'Add Reply';

  @Output() save = new EventEmitter<Reply>();
  @Output() cancel = new EventEmitter<void>();

  onSave() {
    // ✅ Validation simple
    if (!this.reply.content || this.reply.content.trim().length < 3) {
      alert('Reply content is required and must be at least 3 characters.');
      return;
    }
    this.save.emit(this.reply);
  }

  onCancel() { this.cancel.emit(); }
}
