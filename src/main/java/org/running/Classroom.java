package org.running;

import java.sql.Array;
import java.util.*;

import static java.util.Arrays.*;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;

public class Classroom {
    Student[] students;

    public Classroom() {
        students = new Student[30];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(null,null, new Double[0]);
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
        double sum = stream(students)
                .mapToDouble(Student::getAverageExamScore)
                .sum();
        return sum / students.length;
    }

    public void addStudent(Student student) {
        ArrayList<Student> studentsCopy = new ArrayList<>(asList(students));

        if (student != null) {
            studentsCopy.add(student);
        }
//        studentsCopy.removeIf(i -> i.getFirstName() == null && i.getLastName() == null);

        Student[] newStudents;
        newStudents = studentsCopy.toArray(new Student[0]);
        students = newStudents;
    }

    public void removeStudent(String firstName, String lastName) {
        ArrayList<Student> studentsCopy = new ArrayList<>(asList(students));
        studentsCopy.removeIf(i -> i.getFirstName() == null && i.getLastName() == null);
        studentsCopy.removeIf(i -> i.getFirstName().equals(firstName)
                && i.getLastName().equals(lastName));
        Student[] newStudents;
        newStudents = studentsCopy.toArray(new Student[30]);
        students = newStudents;
    }

    public Student[] getStudentsByScore() {
        ArrayList<Student> studentsCopy = new ArrayList<>(asList(students));
        studentsCopy.removeIf(student -> student == null || student.getFirstName() == null || student.getLastName() == null);        studentsCopy.sort(SCORECOMPARATOR);
        Student[] newStudents;
        studentsCopy.sort(SCORECOMPARATOR);
        newStudents = studentsCopy.toArray(new Student[30]);
        return newStudents;
    }

    private static final Comparator<Student> SCORECOMPARATOR =
            comparingDouble((Student s) -> Math.round(s.getAverageExamScore())).reversed()
                    .thenComparing(s-> (String) s.getFirstName())
                    .thenComparing(s -> (String) s.getLastName());

    public int compareTo(Student student1, Student student2) {
        return SCORECOMPARATOR.compare(student1, student2);
    }

    public HashMap<String, ArrayList<Student>> getGradeBook() {
        ArrayList<Student> studentsCopy = new ArrayList<>(asList(students));
        studentsCopy.removeIf(student -> student == null || student.getFirstName() == null || student.getLastName() == null);
        Student studentWithBestScore = studentsCopy.get(0);
        for (Student s : studentsCopy) {
            if (s.getAverageExamScore() > studentWithBestScore.getAverageExamScore()) {
                studentWithBestScore = s;
            }
        }
        double highestAverageScore = studentWithBestScore.getAverageExamScore();

        long valuesBelow = studentsCopy.stream()
                .filter((i -> i.getAverageExamScore() < highestAverageScore))
                .count();
        Map<Student, Double> percentiles = new HashMap<>();

        for (Student s : studentsCopy) {
            double percentile = calculatePercentile(s.getAverageExamScore(), highestAverageScore, valuesBelow, studentsCopy.size());
            percentiles.put(s, percentile);
        }
        HashMap<String, ArrayList<Student>> gradeBook = mapStudentsByGrade(percentiles);
        return gradeBook;
    }

    private double calculatePercentile(double studentScore, double highestScore, long valuesBelow, int totalStudent) {
        return ((valuesBelow + (studentScore == highestScore ? 0.5 : 0)) / totalStudent * 100.0);
    }

    private HashMap<String, ArrayList<Student>> mapStudentsByGrade(Map<Student, Double> percentiles) {
        HashMap<String, ArrayList<Student>> gradeBook = new HashMap<>();
        gradeBook.put("A", new ArrayList<Student>());
        gradeBook.put("B", new ArrayList<Student>());
        gradeBook.put("C", new ArrayList<Student>());
        gradeBook.put("D", new ArrayList<Student>());
        gradeBook.put("F", new ArrayList<Student>());

        double percentileA = 90.0;
        double percentileB = 70.0;
        double percentileC = 50.0;
        double percentileD = 10.0;

        for(Map.Entry<Student, Double> entry : percentiles.entrySet()) {
            Student student = entry.getKey();
            double percentile = entry.getValue();
            String grade;
            if(percentile >= percentileA) {
                grade = "A";
            } else if (percentile >= percentileB) {
                grade = "B";
            } else if (percentile >= percentileC) {
                grade = "C";
            } else if (percentile >= percentileD) {
                grade = "D";
            } else {
                grade = "F";
            }
            gradeBook.computeIfAbsent(grade, k -> new ArrayList<>()).add(student);
        }
        return gradeBook;
    }



}
