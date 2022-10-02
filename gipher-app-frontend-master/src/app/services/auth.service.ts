import { assertPlatform, Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/User';
import { RouterService } from './router.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnInit {
  baseurl: string;
  loggedInUser: User;
  errMessage: string;

  ngOnInit(): void {

  }
  constructor(private httpClient: HttpClient, private routerService: RouterService) {
    this.baseurl = "http://localhost:8888/user";
    this.errMessage = '';
  }

  isAuthenticated() {
    if (sessionStorage.getItem("token"))
      return true;
    else return false;
  }

  isUserAuthenticated() {
    const promise = new Promise(
      (resolve, reject) => {
        if (sessionStorage.getItem('token')) {
          resolve(true);
        } else {
          reject(false);
        }
      }
    );
    return promise;
  }

  getAuth(email, password) {
    const formData = new FormData();
    formData.append('email', email);
    formData.append('password', password);
    this.httpClient.post(`${this.baseurl}/loginUser`, formData)
      .subscribe(data => {
        //console.log(data);
        sessionStorage.setItem("token", data['token']);
        this.getUserData();
        this.routerService.routeToTrending();
      },
        error => {
          console.log("ereoroeoreoroeor");
          alert("Wrong Username & Password combination");
          this.errMessage = 'Wrong Username & Password combination'
        }
      );
  }

  registerUser(user) {
    console.log(user);
    this.httpClient.post(`${this.baseurl}/addNewUser`, user, ({ responseType: "text" }))
      .subscribe(data => { console.log("successfully registered") },
        error => { console.log("Registeration Failed!") }
      );
  }

  getToken() {
    return sessionStorage.getItem('token');
  }

  logoutUser() {
    sessionStorage.removeItem('token');
    this.routerService.routeToTrending();
  }

  getUserData() {
    this.httpClient.get<User>(`${this.baseurl}/findUserByEmail/${this.getToken()}`).subscribe(
      user => { this.loggedInUser = user; console.log(this.loggedInUser) },
      error => console.log(error.message)
    );
  }

  getUserDetails(): Observable<User> {
    return this.httpClient.get<User>(`${this.baseurl}/findUserByEmail/${this.getToken()}`);
  }
  updateUser(user) {
    this.httpClient.put<User>(`${this.baseurl}/updateUser`, user)
      .subscribe(data => {
        console.log(data);
      }, error => {
        console.log(error.message)
      }
      );
  }
  deleteUser(): Observable<string> {
    return this.httpClient.delete<string>(`${this.baseurl}/deleteUser/${this.getToken()}`);
  }
}