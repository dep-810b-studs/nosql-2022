import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import Student from "./student";

@Injectable({
  providedIn: 'root'
})
export class StudentsApiClient {

  constructor(private httpClient: HttpClient) { }

  getAll() : Observable<Student[]>{
    return this.httpClient.get<Student[]>("/api/students");
  }
}
