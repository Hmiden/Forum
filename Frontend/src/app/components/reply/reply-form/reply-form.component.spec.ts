import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule } from '@angular/forms';
import { ReplyFormComponent } from './reply-form.component';

describe('ReplyFormComponent', () => {
  let component: ReplyFormComponent;
  let fixture: ComponentFixture<ReplyFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReplyFormComponent],
      imports: [HttpClientTestingModule, RouterTestingModule, FormsModule]
    });
    fixture = TestBed.createComponent(ReplyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
