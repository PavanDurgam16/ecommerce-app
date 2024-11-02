import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8089/api/auth';
  private jwtToken: string = '';
  private jwtTokenType: string = '';

  constructor(private http: HttpClient) {}

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials);
  }

  saveToken(token: string, tokenType: string) {
    this.jwtTokenType = tokenType;
    this.jwtToken = token;

    localStorage.setItem('token', this.jwtToken);
    localStorage.setItem('jwtTokenType', this.jwtTokenType);

    const decodedToken: any = jwtDecode(token);

    localStorage.setItem('userId', decodedToken.userId);
    localStorage.setItem('username', decodedToken.username);
    localStorage.setItem('userEmail', decodedToken.userEmail);
    localStorage.setItem('roles', JSON.stringify(decodedToken.roles));
  }

  getToken(): string {
    return this.jwtToken || localStorage.getItem('token') || '';
  }

  getUserRoles(): { authority: string }[] {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);

      //console.log('decode token', decodedToken);

      return decodedToken.roles || [];
    }
    return [];
  }

  getUserId() {
    return localStorage.getItem('userId');
  }

  getUserData() {
    return {
      userId: localStorage.getItem('userId'),
      username: localStorage.getItem('username'),
      userEmail: localStorage.getItem('userEmail'),
      roles: JSON.parse(localStorage.getItem('roles') || '[]'),
    };
  }

  clearUserData() {
    this.jwtToken = '';
    this.jwtTokenType = '';
    localStorage.removeItem('token');
    localStorage.removeItem('jwtTokenType');
    localStorage.removeItem('userId');
    localStorage.removeItem('username');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('roles');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  hasRole(role: string): boolean {
    const roles = this.getUserRoles();
    // console.log(roles);
    //console.log(roles.some((userRole) => userRole.authority === role));
    return roles.some((userRole) => userRole.authority === role);
  }

  isAdmin(): boolean {
    return this.hasRole('ROLE_ADMIN');
  }

  isCustomer(): boolean {
    return this.hasRole('ROLE_CUSTOMER');
  }
}
