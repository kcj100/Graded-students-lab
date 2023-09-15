package org.running;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // : Given
        String[] firstNames = {"Josh", "Emma", "Charlie", "Sophia", "Liam", "Olivia", "Aiden", "Ava", "Ethan", "Mia"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Davis", "Wilson", "Lee", "White", "Clark", "Lewis", "Hall"};
        Classroom classroom = new Classroom();
        for (int i = 0; i < 10; i++) {
            String randomFirstName = firstNames[(int) (Math.random() * firstNames.length)];
            String randomLastName = lastNames[(int) (Math.random() * lastNames.length)];

            // Generate random exam scores
            Double[] randomExamScores = new Double[3];
            for (int j = 0; j < 3; j++) {
                randomExamScores[j] = 50.0 + Math.random() * 50.0; // Generates scores between 50.0 and 100.0
            }

            Student student = new Student(randomFirstName, randomLastName, randomExamScores);

            classroom.addStudent(student);

        }
        Student[] output = classroom.getStudents();
        System.out.println(Arrays.toString(output));
    }

}