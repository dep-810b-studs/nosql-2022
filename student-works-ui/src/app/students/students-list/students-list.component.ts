import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {StudentsApiClient} from "../students-api-client.service";
import {Observable} from "rxjs";
import Student from "../student";

@Component({
  selector: 'app-students-list',
  templateUrl: './students-list.component.html',
  styleUrls: ['./students-list.component.css']
})
export class StudentsListComponent implements OnInit {

  @ViewChild('readOnlyTemplate', {static: false}) readOnlyTemplate: TemplateRef<any>|null = null;
  @ViewChild('editTemplate', {static: false}) editTemplate: TemplateRef<any>|null = null;

  editedStudent: Student = {
    id:"1",
    name: "name",
    age:1
  };
  isNewRecord: boolean = false;
  statusMessage: string = "";
  students$: Observable<Student[]> = new Observable<Student[]>();

  constructor(private studentsApiClient: StudentsApiClient) { }

  ngOnInit(): void {
    this.students$ = this.studentsApiClient.getAll();
  }

  addStudent() {

  }

  loadTemplate(student: Student) {
    if (this.editedStudent && this.editedStudent.id == student.id) {
      return this.editTemplate;
    } else {
      return this.readOnlyTemplate;
    }
  }


  cancel() {

  }

  editStudent(student: any) {

  }

  deleteStudent(student: any) {

  }

  saveStudent() {

  }
}
