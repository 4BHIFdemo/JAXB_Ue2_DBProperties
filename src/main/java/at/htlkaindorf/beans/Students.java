package at.htlkaindorf.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
@XmlRootElement
public class Students {
    @XmlElementWrapper(name="students")
    @XmlElement(name="student")
    private List<Student> students;

    public Students() {}

    public Students(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }
}
