import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';
import { AuthService } from 'src/app/services/auth.service';
declare var bootstrap: any;

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {
  categories: Category[] = [];
  showForm: boolean = false;
  currentCategory: Category = { name: '', description: '' };
  selectedCategoryId: number | null = null;

  constructor(
    private categoryService: CategoryService, 
    private router: Router,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadCategories();
  }

 
loadCategories() {
  this.categoryService.getAll().subscribe({
    next: (data) => {
      console.log('Catégories chargées:', data);
      this.categories = data;
    },
    error: (err) => {
      console.error('Erreur chargement categories:', err);
      console.error('Status:', err.status);
      console.error('Message:', err.message);
      console.error('Réponse:', err.error);
    }
  });
}
  // Méthode appelée par le composant enfant via (save)
  handleSave(category: Category) {
    if (category.id) {
      this.categoryService.update(category.id, category).subscribe(() => this.afterSave());
    } else {
      this.categoryService.create(category).subscribe(() => this.afterSave());
    }
  }

  editCategory(category: Category) {
    this.currentCategory = { ...category };
    this.showForm = true;
  }

  cancelForm() {
    this.showForm = false;
    this.currentCategory = { name: '', description: '' };
  }

  // Open modal instead of using confirm()
deleteCategory(id: number) {
  this.selectedCategoryId = id;
  const modal = new bootstrap.Modal(document.getElementById('deleteModal')!);
  modal.show();
}

// Called when user clicks Delete in the modal
confirmDelete() {
  if (this.selectedCategoryId !== null) {
    this.categoryService.delete(this.selectedCategoryId)
      .subscribe(() => {
        this.loadCategories();
        this.selectedCategoryId = null;
        // Hide modal manually
        const modalEl = document.getElementById('deleteModal')!;
        const modal = bootstrap.Modal.getInstance(modalEl);
        modal?.hide();
      });
  }
}
addTopic(category: Category) {
  this.router.navigate(['/categories', category.id, 'topics']);
}
  private afterSave() {
    this.loadCategories();
    this.cancelForm();
  }
}