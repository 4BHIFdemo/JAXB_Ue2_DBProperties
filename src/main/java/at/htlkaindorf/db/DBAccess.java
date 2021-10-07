package at.htlkaindorf.db;

import at.htlkaindorf.beans.Exam;
import at.htlkaindorf.beans.Student;
import at.htlkaindorf.io.CSV_IO;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
public class DBAccess {
    private static DBAccess instance;
    private static PreparedStatement statementGetAllExamsStudentID;
    private static PreparedStatement statementInsertStudent;
    private static PreparedStatement statementInsertExam;

    private DBDatabase dbDatabase;

    public static DBAccess getInstance() {
        if(instance == null)
            instance = new DBAccess();

        return instance;
    }

    private DBAccess() {
        try {
            this.dbDatabase = new DBDatabase(DBProperties.
                    readProperties(new File("src/main/resources/db.properties.xml")));
        }catch(ClassNotFoundException|SQLException|IOException|JAXBException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws SQLException {
        dbDatabase.connect();
    }

    public void disconnect() throws SQLException {
        dbDatabase.disconnect();
    }

    public boolean isConnected() {
        return dbDatabase.isConnected();
    }

    public List<Student> getStudents() throws SQLException {
        Statement stmt = dbDatabase.getStatement();
        List<Student> students = new ArrayList<>();

        ResultSet rs = stmt.executeQuery(Student.SQL_GET_ALL);
        while(rs.next())
            students.add(new Student(rs.getString("name"),
                    rs.getDate("date_of_birth").toLocalDate(), getExams(rs.getInt("student_id"))));

        dbDatabase.releaseStatement(stmt);
        return students;
    }

    public List<Exam> getExams(int studentID) throws SQLException {
        if(statementGetAllExamsStudentID == null)
            statementGetAllExamsStudentID = dbDatabase.getConnection().prepareStatement(Exam.
                    SQL_GET_ALL_WITH_STUDENT_ID);

        Statement stmt = dbDatabase.getStatement();
        List<Exam> exams = new ArrayList<>();

        statementGetAllExamsStudentID.setInt(1, studentID);
        ResultSet rs = statementGetAllExamsStudentID.executeQuery();
        while(rs.next())
            exams.add(new Exam(rs.getString("name"), rs.getShort("mark")));

        dbDatabase.releaseStatement(stmt);
        return exams;
    }

    public boolean insertStudent(Student student) throws SQLException {
        if(statementInsertStudent == null)
            //Statement.RETURN_GENERATED_KEYS: used for getGeneratedKeys(), because primary key is created automatically
            //but is needed for exam
            statementInsertStudent = dbDatabase.getConnection().prepareStatement(Student.SQL_INSERT, Statement.
                    RETURN_GENERATED_KEYS);

        statementInsertStudent.setDate(1, Date.valueOf(student.getDateOfBirth()));
        statementInsertStudent.setString(2, student.getName());
        if(statementInsertStudent.executeUpdate() != 1)
            return false;

        ResultSet rs = statementInsertStudent.getGeneratedKeys();
        if(rs.next()) {
            int studentID = rs.getInt("student_id");
            return insertExams(student.getExamList(), studentID);
        }

        return false;
    }

    public boolean insertStudents(List<Student> students) throws SQLException {
        for(Student student:students)
            if(!insertStudent(student))
                return false;

        return true;
    }

    public boolean insertExam(Exam exam, int studentID) throws SQLException {
        if(statementInsertExam == null)
            statementInsertExam = dbDatabase.getConnection().prepareStatement(Exam.SQL_INSERT);

        statementInsertExam.setString(1, exam.getName());
        statementInsertExam.setShort(2, exam.getMark());
        statementInsertExam.setInt(3, studentID);
        return statementInsertExam.executeUpdate() == 1;
    }

    public boolean insertExams(List<Exam> exams, int studentID) throws SQLException {
        for(Exam exam:exams)
            if(!insertExam(exam, studentID))
                return false;

        return true;
    }

    public void deleteAllStudents() throws SQLException {
        deleteAllExams();

        Statement stmt = dbDatabase.getStatement();
        stmt.executeUpdate(Student.SQL_DELETE_ALL);
        dbDatabase.releaseStatement(stmt);
    }

    public void deleteAllExams() throws SQLException {
        Statement stmt = dbDatabase.getStatement();
        stmt.executeUpdate(Exam.SQL_DELETE_ALL);
        dbDatabase.releaseStatement(stmt);
    }

    public static void main(String[] args) {
        try {
            System.out.println(DBAccess.getInstance().getStudents());
            System.out.println();
            DBAccess.getInstance().deleteAllStudents();
            System.out.println();
            System.out.println(DBAccess.getInstance().getStudents());
            System.out.println();
            System.out.println(DBAccess.getInstance().insertStudents(CSV_IO.readStudentCSV(
                    new File("src/main/resources/students.csv"))));
            System.out.println();
            System.out.println(DBAccess.getInstance().getStudents());
        }catch(SQLException|IOException e) {
            e.printStackTrace();
        }
    }
}
