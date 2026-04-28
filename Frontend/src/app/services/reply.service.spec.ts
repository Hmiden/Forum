import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReplyService } from './reply.service';

describe('ReplyService', () => {
  let service: ReplyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReplyService]
    });
    service = TestBed.inject(ReplyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
