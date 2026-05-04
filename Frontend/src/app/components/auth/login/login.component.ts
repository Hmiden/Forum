import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  registerForm!: FormGroup;
  isLoginMode = true;
  loading = false;
  error = '';
  successMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    // redirect to home if already logged in
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      role: ['USER', Validators.required]
    });
  }

  get lf() { return this.loginForm.controls; }
  get rf() { return this.registerForm.controls; }

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
    this.error = '';
    this.successMessage = '';
  }

  onLoginSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.error = '';
    this.successMessage = '';

    this.authService.login(this.lf['username'].value, this.lf['password'].value)
      .subscribe({
        next: () => {
          this.router.navigate(['/']);
        },
        error: error => {
          this.error = 'Login failed. Please check your credentials.';
          this.loading = false;
        }
      });
  }

  onRegisterSubmit() {
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.error = '';
    this.successMessage = '';

    this.authService.register(
      this.rf['username'].value, 
      this.rf['password'].value,
      this.rf['role'].value
    )
      .subscribe({
        next: (response) => {
          this.successMessage = 'Registration successful! Please login.';
          this.toggleMode(); // switch to login mode
          this.loading = false;
        },
        error: error => {
          this.error = error.error || 'Registration failed. Please try again.';
          this.loading = false;
        }
      });
  }
}
