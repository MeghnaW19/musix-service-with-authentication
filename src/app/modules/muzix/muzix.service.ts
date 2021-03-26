import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { Track } from "./track";
import { text } from "@angular/core/src/render3";
import { USER_NAME } from "../authentication/authentication.service";

@Injectable({
  providedIn: "root"
})
export class MuzixService {
  thirdpartyApi: string;
  apiKey: string;
  springEndPoint: string;
  username: string;

  constructor(private httpClient: HttpClient) {
    this.thirdpartyApi =
      "http://ws.audioscrobbler.com/2.0?method=geo.gettoptracks&country=";
    this.apiKey = "&api_key=3ae92df17c55c4e622cf1ad22e46f87b&format=json";
    // this.springEndPoint = "http://localhost:8083/api/v1/muzixservice/";
   // this.springEndPoint = "http://localhost:8085/api/v1/usertrackservice/";

   this.springEndPoint = "http://localhost:8086/usertrackservice/api/v1/usertrackservice/";
  }

  getTrackDetails(country: string): Observable<any> {
    const url = `${this.thirdpartyApi}${country}${this.apiKey}`;

    return this.httpClient.get(url);
  }

  addTrackToWishList(track: Track) {
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.springEndPoint + "user/" + this.username + "/track";
   // console.log("new url" + url);
    return this.httpClient.post(url, track, {
      observe: "response"
    });
  }

  getAllTracksforWishList(): Observable<Track[]> {
    // const url = this.springEndPoint + "tracks";
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.springEndPoint + "user/" + this.username + "/tracks";
    return this.httpClient.get<Track[]>(url);
  }

  deleteTracKFromWishList(track: Track) {
    this.username = sessionStorage.getItem(USER_NAME);
    // const url = this.springEndPoint + "track/" + `${track.trackId}`;
    const url = this.springEndPoint + "user/" + this.username + "/" + track.trackId;
    // const options = {
    //   headers: new HttpHeaders({
    //     'Content-Type': 'application/json',
    //   }),
    //   body: track
    // };
    console.log("In delete " , track);
   // return this.httpClient.delete(url , options);
   return this.httpClient.delete(url);
  }

  updateComments(track) {
    this.username = sessionStorage.getItem(USER_NAME);
    // const id = track.trackId;

    //  const url = this.springEndPoint + "track/" + `${id}`;
    const url = this.springEndPoint + "user/" + this.username + "/track";
    return this.httpClient.patch(url, track, { observe: "response" });
  }

  filterArtists(tracks: Array<Track>, artistName: string) {
    const results = tracks.filter(track => {
      return track.artist.name.match(artistName);
    });
    console.log("Filetred data", results);

    return results;
  }
}
