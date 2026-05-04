import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Report } from '../models/report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private baseUrl = 'http://localhost:8080/report';

  constructor(private http: HttpClient) {}

  create(replyId: number, reason: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/reply/${replyId}`, { reason });
  }

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admin/all`);
  }

  getPending(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/pending`);
  }

  updateStatus(id: number, status: string): Observable<any> {
    // Note: status is passed as a query param in the backend: @RequestParam ReportStatus status
    return this.http.patch(`${this.baseUrl}/admin/${id}/status?status=${status}`, {});
  }
}