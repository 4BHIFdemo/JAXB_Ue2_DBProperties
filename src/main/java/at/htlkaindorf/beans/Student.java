package at.htlkaindorf.beans;

import at.htlkaindorf.adapter.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) //Fix for getter-Methods of dateOfBirth and examList
public class Student {
    public static final String SQL_GET_ALL = "SELECT * FROM student";
    public static final String SQL_INSERT = "INSERT INTO student (date_of_birth, name) VALUES (?, ?)";
    public static final String SQL_DELETE_ALL = "DELETE FROM student WHERE 1 = 1";

    private String name;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateOfBirth;
    @XmlElementWrapper(name="exams")
    @XmlElement(name="exam")
    private List<Exam> examList;

    public Student() {}

    public Student(String name, LocalDate dateOfBirth, List<Exam> examList) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.examList = examList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Exam> getExamList() {
        return examList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", examList=" + examList +
                '}';
    }
}
