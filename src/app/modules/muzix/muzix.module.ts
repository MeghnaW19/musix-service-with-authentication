import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CardContainerComponent } from "./components/card-container/card-container.component";
import { CardComponent } from "./components/card/card.component";

import { HeaderComponent } from "./components/header/header.component";
import {AngularmaterialModule} from "../angularmaterial/angularmaterial.module";
import { AppRoutingModule } from "../../app-routing.module";
import { WishListComponent } from "./components/wish-list/wish-list.component";
import { FooterComponent } from "./components/footer/footer.component";

import { DialogComponent } from "./components/dialog/dialog.component";
import {MuzixService} from "../muzix/muzix.service";
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { InterceptorService } from "./interceptor.service";

@NgModule({
  declarations: [
    CardContainerComponent,
    CardComponent,
    HeaderComponent,
    WishListComponent,
    FooterComponent,
    DialogComponent
  ],
  imports: [CommonModule, BrowserAnimationsModule, AppRoutingModule, AngularmaterialModule],

  exports: [
    CardContainerComponent,

    HeaderComponent,
    AppRoutingModule,
    FooterComponent,
    WishListComponent
  ],
  providers: [
    MuzixService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService ,
      multi: true
    }
  ],
  entryComponents: [DialogComponent]
})
export class MuzixModule {}
