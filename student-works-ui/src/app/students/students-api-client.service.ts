import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import Student from "./student";

@Injectable({
  providedIn: 'root'
})
export class StudentsApiClient {

  constructor(private httpClient: HttpClient) { }

  create(student: Student): Observable<Object> {
    return this.httpClient.post<Student>(`/api/students`, student);
  }

  getAll() : Observable<Student[]>{
    return this.httpClient.get<Student[]>("/api/students");
  }

  update(studentId: string, student: Partial<Student>): Observable<Student> {
    return this.httpClient.put<Student>(`/api/students/${studentId}`, student);
  }

  delete(studentId: string): Observable<Object> {
    return this.httpClient.delete(`/api/students/${studentId}`);
  }
}
