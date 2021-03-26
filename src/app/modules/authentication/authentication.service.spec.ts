import { TestBed } from "@angular/core/testing";

import { AuthenticationService } from "./authentication.service";
import {
  HttpClientTestingModule,
  HttpTestingController
} from "@angular/common/http/testing";

const testData = {
  username: "test",
  email: "testemail",
  password: "testpassword"
};
describe("AuthenticationService", () => {
  let authService: AuthenticationService;
  let httpTestingController: HttpTestingController;
  const testforRegister =
    "http://localhost:8086/usertrackservice/api/v1/usertrackservice/register";
  const testforLogin =
    "http://localhost:8086/authenticationservice/api/v1/userservice/login";
  beforeEach(() => {
    TestBed.configureTestingModule({
      // This is required to check my service (AuthService) is created or not
      imports: [HttpClientTestingModule],

      providers: [AuthenticationService]
    });

    authService = TestBed.get(AuthenticationService);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  it("should be created", () => {
    // const service: AuthenticationService = TestBed.get(AuthenticationService);
    expect(authService).toBeTruthy();
  });

  it("#registerUser()should fetch proper response from http call", () => {
    const data = testData;
    authService.registerUser(data).subscribe(res => {
      console.log("res data", res);
      expect(res.body).toBe(data);
    });

    const httpMockReq = httpTestingController.expectOne(testforRegister);
    expect(httpMockReq.request.method).toBe("POST");
    expect(httpMockReq.request.url).toEqual(testforRegister);
    httpMockReq.flush(testData);
  });

  it("#login() should fetch proper response from http call", () => {
    authService.loginUser(testData).subscribe(res => {
      expect(res.body).toBe(testData);
    });
    const httpMockReq = httpTestingController.expectOne(testforLogin);
    expect(httpMockReq.request.method).toBe("POST");
    expect(httpMockReq.request.url).toEqual(testforLogin);
    httpMockReq.flush(testData);
  });
});
