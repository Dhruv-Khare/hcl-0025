package CollectionFramework;

import java.util.*;

class Student implements Comparable<Student>{
    String name;
    int age;
    int RollNo;
    double marks;

    Student(String name,int age,int roll,double marks){
        this.name=name;
        this.age=age;
        this.RollNo=roll;
        this.marks=marks;
    }

    @Override
    public int compareTo(Student o) {
        if(this.marks==o.marks){
            return this.age-o.age;
        }
        return (int) (this.marks-o.marks);
    }
}
public class Task {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("Aman", 101, 20, 85.5));
        students.add(new Student("Rahul", 102, 22, 78.0));
        students.add(new Student("Priya", 103, 19, 92.5));
        students.add(new Student("Neha", 104, 21, 88.0));
        students.add(new Student("Rohit", 105, 20, 75.5));
        students.add(new Student("Sneha", 106, 23, 95.0));
        students.add(new Student("Vikas", 107, 18, 81.0));
        students.add(new Student("Anjali", 108, 22, 89.5));
        students.add(new Student("Karan", 109, 21, 72.0));
        students.add(new Student("Pooja", 110, 19, 86.0));

        System.out.println("Normal List:");

        for (Student student : students) {
            System.out.println(student.name+" "+student.age+" "+student.RollNo+" "+student.marks);
        }
        System.out.println("Sorted List By age:");

        Collections.sort(students, Comparator.comparingInt(s -> s.age));
        for (Student student : students) {
            System.out.println(student.name+" "+student.age+" "+student.RollNo+" "+student.marks);
        }
        System.out.println("Sorted List By marks:");

        Collections.sort(students, Comparator.comparingDouble(s -> s.marks));
        for (Student student : students) {
            System.out.println(student.name+" "+student.age+" "+student.RollNo+" "+student.marks);
        }
    }
}
