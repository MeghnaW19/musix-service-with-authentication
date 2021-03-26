import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CardContainerComponent } from "./modules/muzix/components/card-container/card-container.component";
import { WishListComponent } from "./modules/muzix/components/wish-list/wish-list.component";
import { RegisterComponent } from "./modules/authentication/components/register/register.component";
import { LoginComponent } from "./modules/authentication/components/login/login.component";
import { AuthGuardService } from "./modules/muzix/auth-guard.service";

const routes: Routes = [
  {
    path: "Australia",
    component: CardContainerComponent,
    data: { country: "Australia" }
  },
  {
    path: "",
    component: LoginComponent
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "logout",
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: "India",
    component: CardContainerComponent,
    data: { country: "India" }
  },
  {
    path: "Spain",
    component: CardContainerComponent,
    data: { country: "Spain" }
  },

  {
    path: "WishList",
    component: WishListComponent,
    canActivate: [AuthGuardService]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
