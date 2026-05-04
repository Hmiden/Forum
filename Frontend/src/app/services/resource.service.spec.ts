import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ResourceService } from './resource.service';
import { ResourceResponse } from '../models/resource-response';
import { ResourceType } from '../models/resource-type';

describe('ResourceService', () => {
  let service: ResourceService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ResourceService]
    });
    service = TestBed.inject(ResourceService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all resources from the gateway', () => {
    const mockResources: ResourceResponse[] = [
      { id: 1, title: 'Res 1', description: 'Desc 1', url: 'http://1', type: ResourceType.VIDEO, topicId: 10, topicName: 'Topic 1' }
    ];

    service.getAll().subscribe(resources => {
      expect(resources.length).toBe(1);
      expect(resources).toEqual(mockResources);
    });

    const req = httpMock.expectOne('http://localhost:8080/resources');
    expect(req.request.method).toBe('GET');
    req.flush(mockResources);
  });
});
