package brend.vandeneynde.aanwezigheidlogger.repository;

import brend.vandeneynde.aanwezigheidlogger.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student zoekStamnr(String stamnr);
}
