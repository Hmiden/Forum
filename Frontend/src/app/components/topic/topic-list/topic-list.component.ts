import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Topic } from 'src/app/models/topic';
import { TopicService } from 'src/app/services/topic.service';
import { AuthService } from 'src/app/services/auth.service';
declare var bootstrap: any;

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {
  topics: Topic[] = [];
  showForm = false;
  currentTopic: Topic = { title: '', content: '', categoryId: 0, authorEmail: '' };
  categoryId!: number;
  selectedTopicId: number | null = null;

  // ✅ Search variables
  searchTitle: string = '';
  searchDateFrom: string = '';

  constructor(
    private topicService: TopicService,
    private route: ActivatedRoute,
    private router: Router,
    public authService: AuthService
  ) {}

  ngOnInit() {
    this.categoryId = Number(this.route.snapshot.paramMap.get('categoryId'));
    this.loadTopics();
  }

  loadTopics() {
    this.topicService.getByCategory(this.categoryId).subscribe(data => this.topics = data);
  }

  //
  onSearch() {
    if (!this.searchTitle && !this.searchDateFrom) {
      this.loadTopics();
      return;
    }
    this.topicService.search(
      this.searchTitle,
      this.categoryId,
      this.searchDateFrom
    ).subscribe(data => this.topics = data);
  }

  handleSave(topic: Topic) {
    if (topic.id) {
      this.topicService.update(topic.id, topic).subscribe(() => this.afterSave());
    } else {
      this.topicService.create(topic, this.categoryId).subscribe(() => this.afterSave());
    }
  }

  editTopic(topic: Topic) {
    this.currentTopic = { ...topic };
    this.showForm = true;
  }

  cancelForm() {
    this.showForm = false;
    this.currentTopic = { title: '', content: '', categoryId: this.categoryId, authorEmail: '' };
  }

  deleteTopic(id: number) {
    this.selectedTopicId = id;
    const modalEl = document.getElementById('deleteTopicModal')!;
    const modal = new bootstrap.Modal(modalEl);
    modal.show();
  }

  confirmDelete() {
    if (this.selectedTopicId !== null) {
      this.topicService.delete(this.selectedTopicId).subscribe(() => {
        this.loadTopics();
        this.selectedTopicId = null;
        const modalEl = document.getElementById('deleteTopicModal')!;
        const modal = bootstrap.Modal.getInstance(modalEl);
        modal?.hide();
      });
    }
  }

  goToReplies(topic: Topic) {
    this.router.navigate([`/topics/${topic.id}/replies`]);
  }

  goBack() {
    this.router.navigate(['/categories']);
  }

  private afterSave() {
    this.loadTopics();
    this.cancelForm();
  }
}