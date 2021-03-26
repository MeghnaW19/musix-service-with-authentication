import { TestBed } from "@angular/core/testing";

import { MuzixService } from "./muzix.service";
import {
  HttpClientTestingModule,
  HttpTestingController
} from "@angular/common/http/testing";
import { Track } from "./track";
import { Artist } from "./artist";

describe("MuzixService", () => {
  let track = new Track();
  track = {
    trackId: "track123",
    name: "trackname",
    listeners: "123",
    url: "new url",
    comments: "new comments",
    artist: {
      artistId: 123,
      name: "artistname",
      url: "new url",
      image: {
        imageId: 123,
        text: "aa",
        size: "aaa"
      }
    }
  };

  const springEndPoint =
    "http://localhost:8086/usertrackservice/api/v1/usertrackservice/";

  let muzixService: MuzixService;
  let httpTestingController: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MuzixService]
    });

    muzixService = TestBed.get(MuzixService);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  it("should be created", () => {
    const service: MuzixService = TestBed.get(MuzixService);
    expect(service).toBeTruthy();
  });

  it("#addTrackToWishList should fetch proper response from http call", () => {
    muzixService.addTrackToWishList(track).subscribe(res => {
      expect(res.body).toBe(track);
    });

    const url = springEndPoint + "user/" + "test" + "/track";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe("POST");
    expect(httpMockReq.request.url).toEqual(url);
    httpMockReq.flush(track);
  });
  it("#getAllTracksforWishList should fetch proper response from http call", () => {
    muzixService.getAllTracksforWishList().subscribe(res => {
    });

    const url = springEndPoint + "user/" + "test" + "/tracks";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe("GET");
    expect(httpMockReq.request.url).toEqual(url);
    httpMockReq.flush(track);
  });
  it("#deleteTracKFromWishList should fetch proper response from http call", () => {
    muzixService.deleteTracKFromWishList(track).subscribe(res => {
    });

    const url = springEndPoint + "user/" + "test" + "/" + track.trackId;
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe("DELETE");
    expect(httpMockReq.request.url).toEqual(url);
    httpMockReq.flush(track);
  });
  it("#updateComments should fetch proper response from http call", () => {
    muzixService.updateComments(track).subscribe(res => {
      expect(res.body).toBe(track);
    });

    const url = springEndPoint + "user/" + "test" + "/track";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe("PATCH");
    expect(httpMockReq.request.url).toEqual(url);
    httpMockReq.flush(track);
  });

});
