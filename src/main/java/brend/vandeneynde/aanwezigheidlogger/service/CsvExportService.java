package brend.vandeneynde.aanwezigheidlogger.service;

import brend.vandeneynde.aanwezigheidlogger.model.Aanwezigheid;
import brend.vandeneynde.aanwezigheidlogger.model.Student;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CsvExportService {
    @Autowired
    private AanwezigheidService aanwezigheidService;

    public String exportAanwezighedenNaarCsv(List<Aanwezigheid> aanwezigheden) {
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        String[] header = {"ID", "Student ID", "Stamnr", "Naam", "Voornaam", "Email", "Timestamp", "Les/Project", "Opmerking"};
        csvWriter.writeNext(header);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Aanwezigheid aanwezigheid : aanwezigheden) {
            Student student = aanwezigheidService.getStudentById(aanwezigheid.getStudentId());

            String[] data = {
                    String.valueOf(aanwezigheid.getId()),
                    String.valueOf(aanwezigheid.getStudentId()),
                    student != null ? student.getStamnr() : "Onbekend",
                    student != null ? student.getNaam() : "Onbekend",
                    student != null ? student.getVoornaam() : "Onbekend",
                    student != null ? student.getEmail() : "Onbekend",
                    aanwezigheid.getTimestamp().format(formatter),
                    aanwezigheid.getLesOfProject() != null ? aanwezigheid.getLesOfProject() : "",
                    aanwezigheid.getOpmerking() != null ? aanwezigheid.getOpmerking() : ""
            };

            csvWriter.writeNext(data);
        }

        try {
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}
