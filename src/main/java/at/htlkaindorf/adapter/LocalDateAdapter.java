package at.htlkaindorf.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyy");

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return DTF.format(localDate);
    }

    @Override
    public LocalDate unmarshal(String s) throws Exception {
        return LocalDate.parse(s, DTF);
    }
}
