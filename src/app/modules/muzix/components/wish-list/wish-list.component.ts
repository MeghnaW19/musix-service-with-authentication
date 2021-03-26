import { Component, OnInit } from "@angular/core";
import { MuzixService } from "../../muzix.service";
import { Track } from "../../track";
import { MatSnackBar } from "@angular/material";

@Component({
  selector: "app-wish-list",
  templateUrl: "./wish-list.component.html",
  styleUrls: ["./wish-list.component.css"]
})
export class WishListComponent implements OnInit {
  tracks: Array<Track>;
  wishData = true;
  statuscode: number;
  constructor(
    private muzixService: MuzixService,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit() {
    const message = "WishList is empty";
    this.muzixService.getAllTracksforWishList().subscribe(data => {
      this.tracks = data;
      if (data.length === 0) {
        this.snackbar.open(message, " ", {
          duration: 1000
        });
      }
    });
  }

  deleteFromWishList(track) {
    this.muzixService.deleteTracKFromWishList(track).subscribe(data => {
      console.log("deleted", data);
      const index = this.tracks.indexOf(track);
      this.tracks.splice(index, 1);
      this.snackbar.open("successfully deleted", " ", {
        duration: 1000
      });
    });

    return this.tracks;
  }
  updateComments(track) {
    this.muzixService.updateComments(track).subscribe(
      data => {
        this.snackbar.open("Successfully updated", "", {
          duration: 1000
        });
      },
      error => {
        console.log("error", error);
      }
    );
  }
}
