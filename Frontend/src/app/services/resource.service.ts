import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Resource } from '../models/resource';
;
import { ResourceResponse } from '../models/resource-response';

@Injectable({ providedIn: 'root' })
export class ResourceService {

  private baseUrl = 'http://localhost:8078/resources';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ResourceResponse[]> {
    return this.http.get<ResourceResponse[]>(this.baseUrl);
  }

  getByTopic(topicId: number): Observable<ResourceResponse[]> {
    return this.http.get<ResourceResponse[]>(`${this.baseUrl}/topic/${topicId}`);
  }

  create(resource: any): Observable<ResourceResponse> {
    return this.http.post<ResourceResponse>(this.baseUrl, resource);
  }

  update(id: number, resource: any): Observable<ResourceResponse> {
    return this.http.put<ResourceResponse>(`${this.baseUrl}/${id}`, resource);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}