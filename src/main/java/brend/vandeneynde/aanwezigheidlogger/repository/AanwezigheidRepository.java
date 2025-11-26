package brend.vandeneynde.aanwezigheidlogger.repository;

import brend.vandeneynde.aanwezigheidlogger.model.Aanwezigheid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AanwezigheidRepository extends JpaRepository<Aanwezigheid, Integer> {
    List<Aanwezigheid> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
