package org.running;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Classroom {
    Student[] students;

    public Classroom() {
        students = new Student[30];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student("","", new Double[0]);
        }
    }

    public Classroom(int maxNumberOfStudents) {
        students = new Student[maxNumberOfStudents];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(null,null, new Double[0]);
        }
    }

    public Classroom(Student[] students) {
        this.students = students;
    }

    public Student[] getStudents() {
        return students;
    }

    public double getAverageExamScore() {
        double sum = Arrays.stream(students)
                .mapToDouble(Student::getAverageExamScore)
                .sum();
        return sum / students.length;
    }

    public void addStudent(Student student) {
        ArrayList<Student> studentsCopy = new ArrayList<>(Arrays.asList(students));

        if (student != null) {
            studentsCopy.add(student);
        }
        studentsCopy.removeIf(i -> i.getFirstName() == null && i.getLastName() == null);

        Student[] newStudents;
        newStudents = studentsCopy.toArray(new Student[0]);
        students = newStudents;
    }

    public void removeStudent(String firstName, String lastName) {
        ArrayList<Student> studentsCopy = new ArrayList<>(Arrays.asList(students));
        studentsCopy.removeIf(i -> i.getFirstName() == null && i.getLastName() == null);
        studentsCopy.removeIf(i -> i.getFirstName().equals(firstName)
                && i.getLastName().equals(lastName));
        Student[] newStudents;
        newStudents = studentsCopy.toArray(new Student[0]);
        students = newStudents;
    }

    public Student[] getStudentsByScore() {
        Arrays.sort(students, new StudentComparator());
        return students;
    }

    private static class StudentComparator implements Comparator<Student> {

        public int compare(Student student1, Student student2) {
            int scoreComparison = Double.compare(student1.getAverageExamScore(), student2.getAverageExamScore());

            if (scoreComparison == 0) {
                String name1 = student1.getFirstName() + " " + student1.getFirstName();
                String name2 = student2.getFirstName() + " " + student2.getLastName();
                return name1.compareTo(name2);
            }

            return scoreComparison;
        }

    }

}
