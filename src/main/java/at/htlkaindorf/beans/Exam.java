package at.htlkaindorf.beans;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
public class Exam {
    public static final String SQL_GET_ALL_WITH_STUDENT_ID = "SELECT * FROM exam WHERE student_id = ?";
    public static final String SQL_INSERT = "INSERT INTO exam (name, mark, student_id) VALUES (?, ?, ?)";
    public static final String SQL_DELETE_ALL = "DELETE FROM exam WHERE 1 = 1";

    private String name;
    private short mark;

    public Exam() {}

    public Exam(String name, short mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getMark() {
        return mark;
    }

    public void setMark(short mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "name='" + name + '\'' +
                ", mark=" + mark +
                '}';
    }
}
