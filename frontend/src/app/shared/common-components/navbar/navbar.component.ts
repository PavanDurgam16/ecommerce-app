import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  searchQuery: string = '';
  constructor(public authService: AuthService, private router: Router) {}

  logout(): void {
    localStorage.removeItem('token');
    this.authService.clearUserData();
    this.router.navigate(['/auth/login']);
  }

  searchProducts() {
    if (this.searchQuery) {
      console.log('Searching for:', this.searchQuery);
    } else {
      console.warn('Please enter a search query');
    }
  }
}
