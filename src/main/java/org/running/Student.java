package org.running;

import java.util.ArrayList;
import java.util.Arrays;

public class Student {
    private String firstName;
    private String lastName;
    ArrayList<Double> examScores;

    public Student(String firstName, String lastName, ArrayList<Double> examScores) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.examScores = examScores;
    }

    public Student(String firstName, String lastName, Double[] examScores) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.examScores = new ArrayList<>();
        this.examScores.addAll(Arrays.asList(examScores));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExamScores() {
        StringBuilder output = new StringBuilder("Exam Scores: ");
        for (int i = 0; i < examScores.size(); i++) {
            output.append("\n\tExam ").append(i+1).append(" -> ").append(Math.round(examScores.get(i)));
        }
        return output.toString();
    }

    public int getNumberOfExamsTaken() {
        return -1;
    }

    public void addExamScore(double examScore) {
        this.examScores.add(examScore);
    }

    public void setExamScore(int examNumber, double newScore) {
        checkSetExamScoreMethod(examNumber, newScore);
        examScores.set(examNumber - 1, newScore);
    }

    public double getAverageExamScore() {
        return examScores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    private void checkSetExamScoreMethod(int examNumber, double newScore) {
        if (examNumber <= 0) {
            throw new IllegalArgumentException("examNumber is equal to or less than 0");
        }else if (examNumber > examScores.size() + 1) {
            throw new IllegalArgumentException("examNumber is greater than exam amount");
        } else if (newScore < 0) {
            throw new IllegalArgumentException("New score cannot be less than zero");
        }
    }

    public String toString() {
        if (this.firstName == null && this.lastName == null) {
            return null;
        }
        StringBuilder output = new StringBuilder();
        output.append("Student Name: " + this.firstName + " " + this.lastName + "\n> ");
        output.append("Average Score: " + Math.round(getAverageExamScore()) + "\n> ");
        output.append(getExamScores());
        return output.toString();
    }

}
