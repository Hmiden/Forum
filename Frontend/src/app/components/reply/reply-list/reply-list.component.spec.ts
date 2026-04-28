import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule } from '@angular/forms';
import { ReplyListComponent } from './reply-list.component';

describe('ReplyListComponent', () => {
  let component: ReplyListComponent;
  let fixture: ComponentFixture<ReplyListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReplyListComponent],
      imports: [HttpClientTestingModule, RouterTestingModule, FormsModule]
    });
    fixture = TestBed.createComponent(ReplyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
