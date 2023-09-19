package org.running;

import java.sql.Array;
import java.util.*;

import static java.util.Arrays.*;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
public class Classroom {
    Student[] students;

    public Classroom() {
        // default constructor initializes an array of 30 empty student spots
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

    // as according to instructions, I did not make getStudents() of return
    // type void and left it to return Student[], hence the default toString
    // of Array will print
    public Student[] getStudents() {
        System.out.println("CLASSROOM:");
        return students;
    }

    // extra method meant to print out students in desired format
    public void printStudents() {
        Student[] studentsArray = getStudents();
        for (Student student : studentsArray) {
            System.out.println(student);
        }
    }

    public double getAverageExamScore() {
        // use stream to get average
        double sum = stream(students)
                .mapToDouble(Student::getAverageExamScore)
                .sum();
        return sum / students.length;
    }

    public void addStudent(Student student) {
        // create ArrayList for easier manipulation of students array
        ArrayList<Student> studentsCopy = new ArrayList<>(asList(students));
        studentsCopy.removeIf(s -> s == null || s.getFirstName() == null && s.getLastName() == null);
        if (studentsCopy.size() + 1 > students.length) {
            throw new IllegalArgumentException("Classroom is full");
        }
        // verify student is not null
        if (student != null) {
            studentsCopy.add(student);
        }

        // create new Array to convert manipulated ArrayList back into Array
        Student[] newStudents;
        newStudents = studentsCopy.toArray(new Student[students.length]);
        students = newStudents;
    }

    public void removeStudent(String firstName, String lastName) {
        // create ArrayList for easier manipulation of students array
        ArrayList<Student> studentsCopy = new ArrayList<>(asList(students));
        // remove all nulls to verify no nulls are processed during the equals check
        // for firstName and lastName
        studentsCopy.removeIf(s -> s == null || s.getFirstName() == null || s.getLastName() == null);
        studentsCopy.removeIf(i -> i.getFirstName().equals(firstName)
                && i.getLastName().equals(lastName));

        // create new Array to convert manipulated ArrayList back into Array
        Student[] newStudents;
        newStudents = studentsCopy.toArray(new Student[students.length]);
        students = newStudents;
    }

    // as according to instructions, I did not make getStudentsByScore() of return
    // type void and left it to return Student[], hence the default toString
    // of Array will print
    public Student[] getStudentsByScore() {
        System.out.println("CLASSROOM SORTED BY AVERAGE SCORE:");
        // create ArrayList for easier manipulation of students array
        ArrayList<Student> studentsCopy = new ArrayList<>(asList(students));
        // remove all nulls to verify no nulls are processed during the sort
        studentsCopy.removeIf(student -> student == null || student.getFirstName() == null || student.getLastName() == null);
        Student[] newStudents;
        // use custom Comparator to sort by average exam score, then if equal
        // sort lexicographically
        studentsCopy.sort(SCORECOMPARATOR);
        newStudents = studentsCopy.toArray(new Student[students.length]);
        return newStudents;
    }

    // extra method meant to print out students in desired format
    public void printStudentsByScore() {
        Student[] studentsArray = getStudentsByScore();
        for (Student student : studentsArray) {
            System.out.println(student);
        }
    }

    // use custom Comparator of Student type while using lambadas to sort
    // through a Collection, first by average exam score, then if equal
    // sort lexicographically
    private static final Comparator<Student> SCORECOMPARATOR =
            comparingDouble((Student s) -> Math.round(s.getAverageExamScore())).reversed()
                    .thenComparing(s-> (String) s.getFirstName())
                    .thenComparing(s -> (String) s.getLastName());


    public HashMap<String, ArrayList<Student>> getGradeBook() {
        // create ArrayList for easier manipulation of students array
        ArrayList<Student> studentsCopy = new ArrayList<>(asList(students));
        // remove all nulls to verify no nulls are processed during the sort
        studentsCopy.removeIf(student -> student == null || student.getFirstName() == null || student.getLastName() == null);

        // start off with a Student from the first index of ArrayList
        Student studentWithBestScore = studentsCopy.get(0);

        // update studentWithBestScore if next student in for loop has a
        // greater average score
        for (Student s : studentsCopy) {
            if (s.getAverageExamScore() > studentWithBestScore.getAverageExamScore()) {
                studentWithBestScore = s;
            }
        }

        // create double variable of the highest student score received after
        // the for loop
        double highestAverageScore = studentWithBestScore.getAverageExamScore();

        // count all students below the highestAverageScore for percentile formula
        long valuesBelow = studentsCopy.stream()
                .filter((i -> i.getAverageExamScore() < highestAverageScore))
                .count();
        Map<Student, Double> percentiles = new HashMap<>();

        // calculate all students' average score's percentile and
        // insert into percentile HashMap
        for (Student s : studentsCopy) {
            double percentile = calculatePercentile(s.getAverageExamScore(), highestAverageScore, valuesBelow, studentsCopy.size());
            percentiles.put(s, percentile);
        }
        // return students by grade depending on percentile
        return mapStudentsByGrade(percentiles);
    }

    private double calculatePercentile(double studentScore, double highestScore, long valuesBelow, int totalStudent) {
        // percentile formula
        return ((valuesBelow + (studentScore == highestScore ? 0.5 : 0)) / totalStudent * 100.0);
    }

    private HashMap<String, ArrayList<Student>> mapStudentsByGrade(Map<Student, Double> percentiles) {
        // create new HashMap with grades serving as key and ArrayList
        // as the value to store multiple students in each grade
        HashMap<String, ArrayList<Student>> gradeBook = new HashMap<>();
        gradeBook.put("A", new ArrayList<Student>());
        gradeBook.put("B", new ArrayList<Student>());
        gradeBook.put("C", new ArrayList<Student>());
        gradeBook.put("D", new ArrayList<Student>());
        gradeBook.put("F", new ArrayList<Student>());

        // percentile limits
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

    // extra test method to test logic of getGradeBook()
    public void printGradeBook(HashMap<String, ArrayList<Student>> gradeBook) {
        System.out.println("GRADES (CURVED): ");
        for(Map.Entry<String, ArrayList<Student>> entry: gradeBook.entrySet()) {
            StringBuilder grade = new StringBuilder(entry.getKey()).append(" -> ");
            for (Student student : entry.getValue()) {
                grade.append(student.getFirstName() + " " + student.getLastName() + ", ");
            }
            System.out.println(grade.toString().substring(0, grade.length() - 2));
        }
    }



}
