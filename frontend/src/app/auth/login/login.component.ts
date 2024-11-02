import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  userDate: any;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.fb.group({
      usernameOrEmail: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    console.log(this.loginForm);

    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;
      this.authService.login(credentials).subscribe(
        (response: any) => {
          const { token, tokenType } = response;
          this.authService.saveToken(token, tokenType);
          console.log('Login successful:', response);

          this.snackBar.open('Login Successful!', 'Close', {
            duration: 3000,
          });

          if (this.authService.isAdmin()) {
            setTimeout(() => {
              this.router.navigate(['/admin/products']);
            }, 3000);
          } else {
            setTimeout(() => {
              this.router.navigate(['/products']);
            }, 3000);
          }
        },
        (error: any) => {
          console.error('Login failed', error);
          this.snackBar.open(
            'Login Failed. Please check your credentials.',
            'Close',
            {
              duration: 3000,
            }
          );
        }
      );

      this.snackBar.open('Login Successful!', 'Close', {
        duration: 3000,
      });

      setTimeout(() => {
        this.router.navigate(['/products']);
      }, 3000);
    }
  }

  goToRegistration() {
    this.router.navigate(['auth/register']);
  }
}
