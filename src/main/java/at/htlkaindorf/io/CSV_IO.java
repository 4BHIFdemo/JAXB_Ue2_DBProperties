package at.htlkaindorf.io;

import at.htlkaindorf.beans.Exam;
import at.htlkaindorf.beans.Student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
public final class CSV_IO {
    private static DateTimeFormatter STUDENT_CSV_DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private CSV_IO() {}

    public static List<Student> readStudentCSV(File file) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines().map(CSV_IO::parseStudent).collect(Collectors.toList());
        }
    }
    private static Student parseStudent(String line) {
        String[] tokens = line.split(";");
        if(tokens.length < 2)
            throw new IllegalArgumentException("line has not enough tokens");

        LocalDate dateOfBirth = LocalDate.parse(tokens[0], STUDENT_CSV_DTF);
        String name = tokens[1];
        List<Exam> examList = new ArrayList<>();
        for(int i = 2;i < tokens.length;i++) {
            String examData = tokens[i];
            String[] examTokens = examData.split("#");
            if(examTokens.length != 2)
                throw new IllegalArgumentException("line contains invalid exam data");

            String examName = examTokens[0];
            short examMark = Short.parseShort(examTokens[1]);
            examList.add(new Exam(examName, examMark));
        }

        return new Student(name, dateOfBirth, examList);
    }

    public static void main(String[] args) {
        try {
            System.out.println(CSV_IO.readStudentCSV(new File("src/main/resources/students.csv")));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
