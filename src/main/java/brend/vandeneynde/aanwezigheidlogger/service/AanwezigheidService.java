package brend.vandeneynde.aanwezigheidlogger.service;

import brend.vandeneynde.aanwezigheidlogger.dto.AanwezigheidRequest;
import brend.vandeneynde.aanwezigheidlogger.model.Aanwezigheid;
import brend.vandeneynde.aanwezigheidlogger.model.Student;
import brend.vandeneynde.aanwezigheidlogger.repository.AanwezigheidRepository;
import brend.vandeneynde.aanwezigheidlogger.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AanwezigheidService {
    private StudentRepository studentRepository;
    private AanwezigheidRepository aanwezigheidRepository;

    public Aanwezigheid registreerAanwezigheid(AanwezigheidRequest request) throws Exception{
        Student student = studentRepository.zoekStamnr(request.getStamnr());

        if (student == null) {
            throw new Exception("Student met stamnummer " + request.getStamnr() + " niet gevonden");
        }

        Aanwezigheid aanwezigheid = new Aanwezigheid();
        aanwezigheid.setStudentId(student.getId());
        aanwezigheid.setLesOfProject(request.getLesOfProject());
        aanwezigheid.setTimestamp(LocalDateTime.now());

        return aanwezigheidRepository.save(aanwezigheid);
    }
}
