import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { CategoryListComponent } from './components/category/category-list/category-list.component';
import { CategoryFormComponent } from './components/category/category-form/category-form.component';
import { TopicFormComponent } from './components/topic/topic-form/topic-form.component';
import { TopicListComponent } from './components/topic/topic-list/topic-list.component';
import { ReplyListComponent } from './components/reply/reply-list/reply-list.component';
import { ReplyFormComponent } from './components/reply/reply-form/reply-form.component';
import { ResourceListComponent } from './components/resource-list/resource-list.component';
import { SafePipe } from './pipes/safe.pipe';
import { LoginComponent } from './components/auth/login/login.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    CategoryListComponent,
    CategoryFormComponent,
    TopicFormComponent,
    TopicListComponent,
    ReplyListComponent,
    ReplyFormComponent,
    ResourceListComponent,
    SafePipe,
    LoginComponent,
    AdminDashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
