import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryListComponent } from './components/category/category-list/category-list.component';
import { TopicListComponent } from './components/topic/topic-list/topic-list.component';
import { ReplyListComponent } from './components/reply/reply-list/reply-list.component';
import { ResourceListComponent } from './components/resource-list/resource-list.component';
import { LoginComponent } from './components/auth/login/login.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';

const routes: Routes = [
  { path: '', redirectTo: '/categories', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'categories', component: CategoryListComponent },
  { path: 'categories/:categoryId/topics', component: TopicListComponent },
  
  { path: 'topics/:topicId/replies', component: ReplyListComponent },
  { path: 'resources', component: ResourceListComponent },
  { path: 'admin', component: AdminDashboardComponent },
  { path: '**', redirectTo: '/categories' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
