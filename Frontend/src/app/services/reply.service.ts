import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Reply } from '../models/reply';
import { ReplyDto } from '../models/reply-dto';


@Injectable({
  providedIn: 'root'
})
export class ReplyService {

  private baseUrl = 'http://localhost:8080/reply';

  constructor(private http: HttpClient) { }
getByTopic(topicId: number): Observable<ReplyDto[]> {
  return this.http.get<ReplyDto[]>(`${this.baseUrl}/topic/${topicId}`);
}

  create(reply: Reply, topicId: number): Observable<ReplyDto> {
    return this.http.post<ReplyDto>(`${this.baseUrl}/topic/${topicId}`, reply);
  }

  update(id: number, reply: Reply): Observable<ReplyDto> {
    return this.http.put<ReplyDto>(`${this.baseUrl}/${id}`, reply);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  
}
