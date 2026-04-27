import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Category } from 'src/app/models/category';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent {
    @Input() category: Category = { name: '', description: '' };
  @Input() title: string = 'Add Category';
  @Output() save = new EventEmitter<Category>();
  @Output() cancel = new EventEmitter<void>();

  onSave() {
    // ✅ Contrôle simple avant sauvegarde
    if (!this.category.name || this.category.name.trim().length < 3) {
      alert('Category name is required and must be at least 3 characters.');
      return;
    }
    this.save.emit(this.category);
  }

  onCancel() {
    this.cancel.emit();
  }

}
