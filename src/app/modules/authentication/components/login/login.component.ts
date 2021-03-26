import { Component, OnInit } from "@angular/core";
import { User } from "../../User";
import { AuthenticationService } from "../../authentication.service";
import { MatSnackBar } from "@angular/material";
import { Router } from "@angular/router";

export const TOKEN_NAME = "jwt_token";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
})
export class LoginComponent implements OnInit {
  user: User;
  constructor(
    private authService: AuthenticationService,
    private snackbar: MatSnackBar,
    private router: Router
  ) {
    this.user = new User();
  }

  login() {
    this.authService.loginUser(this.user).subscribe(
      data => {
        console.log(data);
        if (data) {
          console.log("token coming", data.body["token"]);
          localStorage.setItem(TOKEN_NAME, data.body["token"]);
          this.snackbar.open(data.body["message"], " ", {
            duration: 1000
          });
          this.router.navigate(["/India"]);
        }
      },
      
      error => {
        console.log("error", error);
        if (error.status === 404) {
          const errorMsg = error.error.message;
          this.snackbar.open(errorMsg, " ", {
            duration: 1000
          });
        }
      }
    );
  }

  logout() {
    this.authService.logout();
    console.log("In logout logout");
  }
  ngOnInit() {}
}
