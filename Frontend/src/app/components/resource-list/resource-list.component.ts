import { Component, OnInit } from '@angular/core';
import { ResourceService } from 'src/app/services/resource.service';
import { TopicService } from 'src/app/services/topic.service';
import { AuthService } from 'src/app/services/auth.service';

import { ResourceResponse } from 'src/app/models/resource-response';
import { Resource } from 'src/app/models/resource';
import { ResourceType } from 'src/app/models/resource-type';
import { Topic } from 'src/app/models/topic';

@Component({
  selector: 'app-resource-list',
  templateUrl: './resource-list.component.html',
  styleUrls: ['./resource-list.component.css']
})
export class ResourceListComponent implements OnInit {

  // =========================
  // DATA
  // =========================
  resources: ResourceResponse[] = [];
  filtered: ResourceResponse[] = [];
  topics: Topic[] = [];

  // =========================
  // FORM
  // =========================
  showForm = false;
  isEditing = false;
  current: Resource = this.emptyResource();

  // =========================
  // ENUM
  // =========================
  types = Object.values(ResourceType);

  // =========================
  // FILTERS
  // =========================
  filterTitle = '';
  filterType: ResourceType | '' = '';

  // =========================
  // PREVIEW
  // =========================
  openPreviewId: number | null = null;

  // =========================
  // MSG
  // =========================
  successMsg = '';
  errorMsg = '';

  constructor(
    private resourceService: ResourceService,
    private topicService: TopicService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.load();
    this.loadTopics();
  }

  // =========================
  // LOAD
  // =========================
  load(): void {
    this.resourceService.getAll().subscribe({
      next: (data) => { this.resources = data; this.applyFilters(); },
      error: () => this.showError('Failed to load resources.')
    });
  }

  loadTopics(): void {
    this.topicService.getAllDTO().subscribe({
      next: (data) => { this.topics = data; },
      error: () => this.showError('Failed to load topics from Forum service.')
    });
  }

  // =========================
  // FILTERS
  // =========================
  applyFilters(): void {
    this.filtered = this.resources.filter(r => {
      const matchTitle = !this.filterTitle ||
        r.title.toLowerCase().includes(this.filterTitle.toLowerCase());
      const matchType = !this.filterType || r.type === this.filterType;
      return matchTitle && matchType;
    });
  }

  // =========================
  // PREVIEW
  // =========================
  togglePreview(id: number): void {
    this.openPreviewId = this.openPreviewId === id ? null : id;
  }

  /**
   * Converts a raw YouTube URL to an embed URL.
   * e.g. https://www.youtube.com/watch?v=XYZ → https://www.youtube.com/embed/XYZ
   * Returns the original URL unchanged for non-YouTube links.
   */
  getEmbedUrl(url: string): string {
    const yt = url.match(/(?:youtube\.com\/watch\?v=|youtu\.be\/)([^&\s]+)/);
    if (yt) return `https://www.youtube.com/embed/${yt[1]}`;
    return url;
  }

  // =========================
  // FORM ACTIONS
  // =========================
  openCreate(): void {
    this.current = this.emptyResource();
    this.isEditing = false;
    this.showForm = true;
  }

  openEdit(r: ResourceResponse): void {
    this.current = {
      id: r.id,
      title: r.title,
      description: r.description,
      url: r.url,
      type: r.type as ResourceType,
      topicId: Number(r.topicId)
    };
    this.isEditing = true;
    this.showForm = true;
  }

  cancelForm(): void {
    this.showForm = false;
    this.current = this.emptyResource();
  }

  // =========================
  // SAVE
  // =========================
  save(): void {
    this.current.topicId = Number(this.current.topicId);

    if (!this.current.title || !this.current.url || !this.current.topicId) {
      this.showError('Title, URL and Topic are required.');
      return;
    }

    if (this.isEditing && this.current.id) {
      this.resourceService.update(this.current.id, this.current).subscribe({
        next: () => { this.showSuccess('Resource updated.'); this.cancelForm(); this.load(); },
        error: () => this.showError('Update failed.')
      });
    } else {
      this.resourceService.create(this.current).subscribe({
        next: () => { this.showSuccess('Resource created.'); this.cancelForm(); this.load(); },
        error: () => this.showError('Creation failed.')
      });
    }
  }

  // =========================
  // DELETE
  // =========================
  delete(id: number): void {
  if (!confirm('Delete this resource?')) return;

  this.resourceService.delete(id).subscribe({
    next: () => {
      // REMOVE FROM LIST DIRECTLY (propre UI)
      this.resources = this.resources.filter(r => r.id !== id);
      this.applyFilters();

      this.showSuccess('Resource deleted.');
    },
    error: (err) => {
        const msg = err.error?.message || err.error || 'Delete failed.';
        this.showError(msg);
      }
  });
}

  // =========================
  // HELPERS
  // =========================
  private emptyResource(): Resource {
    return { title: '', description: '', url: '', type: ResourceType.LINK, topicId: 0 };
  }

  private showSuccess(msg: string): void {
    this.successMsg = msg; this.errorMsg = '';
    setTimeout(() => (this.successMsg = ''), 3000);
  }

  private showError(msg: string): void {
    this.errorMsg = msg; this.successMsg = '';
    setTimeout(() => (this.errorMsg = ''), 3000);
  }
}