import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { GiphyAPIService } from '../services/giphy-api.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Gif, Giffy } from '../model/Gif';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-gif-details',
  templateUrl: './gif-details.component.html',
  styleUrls: ['./gif-details.component.css']
})
export class GifDetailsComponent implements OnInit {
  @Input() data: Gif;
  @Input() data1: Giffy;
  email: string;
  index: number;
  selectedGif: Gif;
  recommendedString = "Recommend";

  constructor(private gifApi: GiphyAPIService, private activatedRoute: ActivatedRoute, private authService: AuthService, private routerService: RouterService) {
  }
  ngOnInit() {
    this.gifApi.fetchGifById(this.activatedRoute.snapshot.params['id']).subscribe(
      data => {
        this.selectedGif = new Gif();
        this.selectedGif.id = data["data"]["id"];
        this.selectedGif.username = data["data"]["username"];
        this.selectedGif.title = data["data"]["title"];
        this.selectedGif.isSticker = data["data"]["is_sticker"];
        this.selectedGif.imageURL = data["data"]["images"]["downsized_large"]["url"];
        this.authService.getUserDetails().subscribe(
          user => {
            this.gifApi.recommended.forEach(gif => {
              if (gif.id === this.selectedGif.id) {
                this.selectedGif.recommended = gif.recommended;
                var index = gif.recommended.findIndex(email => email === user.emailId);
                if (index === -1) {
                  this.recommendedString = "Recommend";
                } else {
                  this.recommendedString = "Unrecommend";
                }
              }
            })
          }
        );
      }, error => console.log(error.message)
    );

  }

  onClickFavourite() {
    
    if (this.authService.isAuthenticated()) {
      this.index = this.gifApi.favourites.findIndex((giffy: Giffy) => giffy.favId == this.selectedGif.id);

      var gifData: Giffy = {
        "favId": this.selectedGif.id,
        "username": this.selectedGif.username,
        "emailId": this.authService.loggedInUser.emailId,
        "title": this.selectedGif.title,
        "imageURL": this.selectedGif.imageURL,
        "isSticker": this.selectedGif.isSticker
      }
      console.log(gifData.imageURL);
      if (this.index == -1) {
        this.gifApi.addToFavourites(gifData);

        this.gifApi.favourites.forEach((giffy: Giffy) => {
          console.log(giffy.favId);
        })
      }
      else {
        console.log("cdd");
        console.log(this.gifApi.favourites.findIndex((giffy: Giffy) => giffy.favId === (this.selectedGif.id)));
        
        this.gifApi.favourites.splice(
          this.gifApi.favourites.findIndex((giffy: Giffy) => giffy.favId === this.selectedGif.id), 1);
        this.gifApi.deleteFromFavourites(gifData);
      }
    }
    else {
      this.routerService.routeToLogin();
    }
  }
  onCheckFavourites() {
    if (this.gifApi.favourites.findIndex((giffy: Giffy) => this.selectedGif.id == giffy.favId) !== -1) {
      return true;
    }
    return false;
  }
}
