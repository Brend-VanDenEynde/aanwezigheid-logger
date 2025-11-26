package brend.vandeneynde.aanwezigheidlogger.repository;

import brend.vandeneynde.aanwezigheidlogger.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student zoekStamnr(String stamnr);
}
