import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../models/topic';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

 private baseUrl = 'http://localhost:8080/topic';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.baseUrl);
  }

  getByCategory(categoryId: number): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.baseUrl}/category/${categoryId}`);
  }

  create(topic: Topic, categoryId: number): Observable<Topic> {
    return this.http.post<Topic>(`${this.baseUrl}/category/${categoryId}`, topic);
  }

  update(id: number, topic: Topic): Observable<Topic> {
    return this.http.put<Topic>(`${this.baseUrl}/${id}`, topic);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  search(title?: string, categoryId?: number, dateFrom?: string): Observable<Topic[]> {
    let params: any = {};
    if (title)      params.title = title;
    if (categoryId) params.categoryId = categoryId;
    if (dateFrom)   params.dateFrom = dateFrom;
    return this.http.get<Topic[]>(`${this.baseUrl}/search`, { params });
  }
  

   
  // ✅ FIX: Retourne List<TopicDTO> — id + title proprement, sans objets imbriqués
  // Utiliser cette méthode pour le dropdown dans resource-list
  getAllDTO(): Observable<Topic[]> {
  return this.http.get<Topic[]>(`${this.baseUrl}/dto`);
  }
  
}
