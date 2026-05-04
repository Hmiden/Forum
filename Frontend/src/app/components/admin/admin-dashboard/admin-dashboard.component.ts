import { Component, OnInit } from '@angular/core';
import { BadwordService } from 'src/app/services/badword.service';
import { ReportService } from 'src/app/services/report.service';
import { ResourceService } from 'src/app/services/resource.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  activeTab: 'REPORTS' | 'BADWORDS' | 'RESOURCES' = 'REPORTS';

  // Badwords
  badwords: any[] = [];
  newBadword: string = '';

  // Reports
  reports: any[] = [];

  // Resources
  resources: any[] = [];

  constructor(
    private badwordService: BadwordService,
    private reportService: ReportService,
    private resourceService: ResourceService
  ) {}

  ngOnInit(): void {
    this.loadReports();
    this.loadBadwords();
    this.loadResources();
  }

  // --- Tabs ---
  switchTab(tab: 'REPORTS' | 'BADWORDS' | 'RESOURCES') {
    this.activeTab = tab;
  }

  // --- Badwords ---
  loadBadwords() {
    this.badwordService.getAll().subscribe(data => this.badwords = data);
  }

  addBadword() {
    if (this.newBadword.trim()) {
      this.badwordService.add(this.newBadword.trim()).subscribe({
        next: () => {
          this.newBadword = '';
          this.loadBadwords();
        },
        error: (err) => alert('Error adding badword: ' + (err.error?.message || err.message))
      });
    }
  }

  deleteBadword(id: number) {
    if (confirm('Are you sure you want to delete this badword?')) {
      this.badwordService.delete(id).subscribe(() => this.loadBadwords());
    }
  }

  // --- Reports ---
  loadReports() {
    this.reportService.getAll().subscribe(data => this.reports = data);
  }

  updateReportStatus(id: number, status: string) {
    this.reportService.updateStatus(id, status).subscribe(() => this.loadReports());
  }

  // --- Resources ---
  loadResources() {
    this.resourceService.getAll().subscribe(data => this.resources = data);
  }

  updateResourceStatus(id: number, status: string) {
    this.resourceService.updateStatus(id, status).subscribe(() => this.loadResources());
  }

  getBadgeClass(status: string) {
    switch(status) {
      case 'PENDING': return 'bg-warning text-dark';
      case 'REVIEWED': return 'bg-success';
      case 'APPROVED': return 'bg-success';
      case 'REJECTED': return 'bg-danger';
      case 'DISMISSED': return 'bg-secondary';
      default: return 'bg-primary';
    }
  }
}
