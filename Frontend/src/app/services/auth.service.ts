import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(localStorage.getItem('token'));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/login`, { username, password })
      .pipe(map(response => {
        // login successful if there's a jwt token in the response
        if (response && response.token) {
          // store jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('token', response.token);
          this.currentUserSubject.next(response.token);
        }
        return response;
      }));
  }

  register(username: string, password: string, role: string = 'USER'): Observable<any> {
    // The backend register endpoint returns a plain string, so we need responseType: 'text'
    return this.http.post(`${this.baseUrl}/register`, { username, password, role }, { responseType: 'text' });
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('token');
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return !!this.currentUserValue;
  }

  decodeToken(token: string): any {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));

      return JSON.parse(jsonPayload);
    } catch (e) {
      return null;
    }
  }

  isAdmin(): boolean {
    const token = this.currentUserValue;
    if (!token) return false;
    
    const decoded = this.decodeToken(token);
    if (!decoded || !decoded.role) return false;

    // Accepte "ADMIN" ou "ROLE_ADMIN"
    return decoded.role === 'ADMIN' || decoded.role === 'ROLE_ADMIN';
  }

  getUsername(): string {
    const token = this.currentUserValue;
    if (!token) return '';
    const decoded = this.decodeToken(token);
    return decoded ? decoded.sub : '';
  }
}
