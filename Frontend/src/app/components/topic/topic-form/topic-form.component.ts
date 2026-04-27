import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Topic } from 'src/app/models/topic';

@Component({
  selector: 'app-topic-form',
  templateUrl: './topic-form.component.html',
  styleUrls: ['./topic-form.component.css']
})
export class TopicFormComponent {

  @Input() topic: Topic = { title: '', content: '', categoryId: 0, authorEmail: '' };
  @Input() titleForm: string = 'Add Topic';

  @Output() save = new EventEmitter<Topic>();
  @Output() cancel = new EventEmitter<void>();

  onSave() {
    if (!this.topic.title || this.topic.title.trim().length < 3) {
      alert('Title is required and must be at least 3 characters.');
      return;
    }
    if (!this.topic.content || this.topic.content.trim().length < 3) {
      alert('Content is required and must be at least 3 characters.');
      return;
    }
    if (!this.topic.authorEmail || !this.topic.authorEmail.includes('@')) {
      alert('Please enter a valid email address.');
      return;
    }
    this.save.emit(this.topic);
  }

  onCancel() { this.cancel.emit(); }
}