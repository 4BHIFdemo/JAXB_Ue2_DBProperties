package at.htlkaindorf.bl;

import at.htlkaindorf.beans.Student;
import at.htlkaindorf.db.DBAccess;
import at.htlkaindorf.io.CSV_IO;
import at.htlkaindorf.io.XML_IO;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
public final class Tester {
    private static final File FILE_STUDENTS_CSV = new File("src/main/resources/students.csv");
    private static final File FILE_STUDENTS_XML= new File("src/main/resources/students.xml");

    public static void main(String[] args) {
        try {
            List<Student> students = CSV_IO.readStudentCSV(FILE_STUDENTS_CSV);
            System.out.println("Read students from CSV file " + FILE_STUDENTS_CSV.getName() + ":");
            students.stream().map(student -> "\t" + student).forEach(System.out::println);
/*

            System.out.println("Deleting old students from database...");
            DBAccess.getInstance().deleteAllStudents();

            System.out.println("Writing students to database...");
            DBAccess.getInstance().insertStudents(students);
            System.out.println("Read students from DB:");
            DBAccess.getInstance().getStudents().stream().map(student -> "\t" + student).forEach(System.out::println);

*/
            System.out.println("Writing students to XML file " + FILE_STUDENTS_XML.getName() + ": ");
            //XML_IO.writeStudents(DBAccess.getInstance().getStudents(), FILE_STUDENTS_XML);
            XML_IO.writeStudents(students, FILE_STUDENTS_XML);
            System.out.println("Read students from XML file " + FILE_STUDENTS_XML.getName() + ": ");
            XML_IO.readStudents(FILE_STUDENTS_XML).stream().map(student -> "\t" + student).forEach(System.out::println);
        }catch(IOException/*|SQLException*/|JAXBException e) {
            e.printStackTrace();
        }
    }
}
