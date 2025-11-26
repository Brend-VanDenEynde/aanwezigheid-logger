package brend.vandeneynde.aanwezigheidlogger.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "aanwezigheden")
public class Aanwezigheid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "student_id")
    private int studentId;
    private LocalDateTime timestamp;
    @Column(name = "les_of_project")
    private String lesOfProject;
    private String opmerking;

    public Aanwezigheid() {
        this.timestamp = LocalDateTime.now();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getLesOfProject() {
        return lesOfProject;
    }

    public void setLesOfProject(String lesOfProject) {
        this.lesOfProject = lesOfProject;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }
}
