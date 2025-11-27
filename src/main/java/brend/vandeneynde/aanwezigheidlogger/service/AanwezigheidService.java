package brend.vandeneynde.aanwezigheidlogger.service;

import brend.vandeneynde.aanwezigheidlogger.dto.AanwezigheidRequest;
import brend.vandeneynde.aanwezigheidlogger.model.Aanwezigheid;
import brend.vandeneynde.aanwezigheidlogger.model.Student;
import brend.vandeneynde.aanwezigheidlogger.repository.AanwezigheidRepository;
import brend.vandeneynde.aanwezigheidlogger.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AanwezigheidService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AanwezigheidRepository aanwezigheidRepository;

    public Aanwezigheid registreerAanwezigheid(AanwezigheidRequest request) throws Exception{
        Student student = studentRepository.findByStamnr(request.getStamnr());

        if (student == null) {
            throw new Exception("Student met stamnummer " + request.getStamnr() + " niet gevonden");
        }

        Aanwezigheid aanwezigheid = new Aanwezigheid();
        aanwezigheid.setStudentId(student.getId());
        aanwezigheid.setLesOfProject(request.getLesOfProject());
        aanwezigheid.setTimestamp(LocalDateTime.now());

        return aanwezigheidRepository.save(aanwezigheid);
    }

    public List<Aanwezigheid> getAlleAanwezigheden() {
        return aanwezigheidRepository.findAll();
    }

    public Student getStudentById(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public List<Aanwezigheid> getAanwezighedenVoorDag(LocalDateTime datum) {
        LocalDateTime startVanDag = datum.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime eindeVanDag = datum.withHour(23).withMinute(59).withSecond(59);

        return aanwezigheidRepository.findByTimestampBetween(startVanDag, eindeVanDag);
    }
}
