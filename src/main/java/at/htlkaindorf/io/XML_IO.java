package at.htlkaindorf.io;

import at.htlkaindorf.beans.Student;
import at.htlkaindorf.beans.Students;
import at.htlkaindorf.db.DBProperties;

import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
public final class XML_IO {
    private XML_IO() {}

    public static void writeDBProperties(DBProperties properties, File file) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(DBProperties.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(properties, file);
    }

    public static DBProperties readDBProperties(File file) {
        return JAXB.unmarshal(file, DBProperties.class);
    }

    public static void writeStudents(List<Student> students, File file) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Students.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(new Students(students), file);
    }

    public static List<Student> readStudents(File file) {
        return JAXB.unmarshal(file, Students.class).getStudents();
    }

    public static void main(String[] args) {
        File file = new File("src/main/resources/test.xml");
        file.delete();

        File file2 = new File("src/main/resources/test2.xml");
        file2.delete();

        try {
            XML_IO.writeDBProperties(DBPropertyReader.getProperties(), file);
            System.out.println(XML_IO.readDBProperties(file));

            writeStudents(CSV_IO.readStudentCSV(new File("src/main/resources/students.csv")), file2);
        }catch(IOException|JAXBException e) {
            e.printStackTrace();
        }
    }
}
