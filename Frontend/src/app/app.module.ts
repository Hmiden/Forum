import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CategoryListComponent } from './components/category/category-list/category-list.component';
import { CategoryFormComponent } from './components/category/category-form/category-form.component';
import { TopicFormComponent } from './components/topic/topic-form/topic-form.component';
import { TopicListComponent } from './components/topic/topic-list/topic-list.component';
import { ReplyListComponent } from './components/reply/reply-list/reply-list.component';
import { ReplyFormComponent } from './components/reply/reply-form/reply-form.component';
import { ResourceListComponent } from './components/resource-list/resource-list.component';
import { SafePipe } from './pipes/safe.pipe';

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
    SafePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
