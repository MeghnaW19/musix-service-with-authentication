import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import {AuthenticationModule} from "./modules/authentication/authentication.module";
import { MuzixModule } from "./modules/muzix/muzix.module";
import { HttpClientModule } from "@angular/common/http";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";


@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    MuzixModule,
    BrowserAnimationsModule,
    AuthenticationModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
