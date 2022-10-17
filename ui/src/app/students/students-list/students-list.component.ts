import {Component, Input, OnInit, TemplateRef, ViewChild} from "@angular/core";
import { StudentsApiClient } from "../students-api-client.service";
import Student from "../student";
import {map, Observable, Subject} from "rxjs";

@Component({
  selector: 'app-students-list',
  templateUrl: './students-list.component.html',
  styleUrls: ['./students-list.component.css']
})
export class StudentsListComponent implements OnInit {

  @ViewChild('readOnlyTemplate', {static: false}) readOnlyTemplate: TemplateRef<any>|null = null;
  @ViewChild('editTemplate', {static: false}) editTemplate: TemplateRef<any>|null = null;

  editedStudent: Student | undefined;
  isNewRecord: boolean = false;
  statusMessage: string = "";
  students$: Subject<Student[]> | undefined = new Subject<Student[]>();
  students: Student[] | undefined;

  constructor(private studentsApiClient: StudentsApiClient) { }

  @Input()
  set searchText(searchText: string) {
    this.loadStudents(searchText);
  }

  ngOnInit(): void {
    this.students$?.subscribe(students => this.students = students);
    this.loadStudents();
  }

  loadTemplate(student: Student) {
    if (this.editedStudent && this.editedStudent.id == student.id) {
      return this.editTemplate;
    } else {
      return this.readOnlyTemplate;
    }
  }

  private loadStudents(searchText: string = "") {
    let studentsObservable: Observable<Student[]> = searchText
      ? this.studentsApiClient.find(searchText)
      : this.studentsApiClient.getAll();

    studentsObservable
      .pipe(
        map(students => students.sort((one, two) => (one.id > two.id ? 1 : -1)))
      )
      .subscribe(students => this.students$?.next(students));
  }

  addStudent() {
    this.editedStudent = {
      id:"",
      name: "",
      age:0
    };
    this.students?.push(this.editedStudent);
    this.isNewRecord = true;
  }

  editStudent(student: any) {
    this.editedStudent = {
      id: student.id,
      name: student.name,
      age: student.age
    }
  }

  deleteStudent(student: any) {
    this.studentsApiClient
      .delete(student.id)
      .subscribe(() =>{
          this.statusMessage = 'Данные успешно удалены';
          this.loadStudents();
      });
  }

  cancel() {
    this.loadStudents();
  }

  saveStudent() {
    if (this.isNewRecord) {
      this.studentsApiClient
        .create(this.editedStudent!)
        .subscribe(() => {
          this.statusMessage = 'Данные успешно добавлены';
          this.editedStudent = undefined;
          this.loadStudents();
        });
      this.isNewRecord = false;
    } else {
      this.studentsApiClient
        .update(this.editedStudent?.id!, {
          name: this.editedStudent?.name!,
          age: this.editedStudent?.age!
        })
        .subscribe(() => {
        this.statusMessage = 'Данные успешно обновлены';
          this.loadStudents();
      });
    }
  }
}
