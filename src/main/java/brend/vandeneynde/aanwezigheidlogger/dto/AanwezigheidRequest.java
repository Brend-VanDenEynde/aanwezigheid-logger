package brend.vandeneynde.aanwezigheidlogger.dto;

public class AanwezigheidRequest {
    private String stamnr;
    private String lesOfProject;

    public AanwezigheidRequest(){

    }

    public String getStamnr() {
        return stamnr;
    }

    public void setStamnr(String stamnr) {
        this.stamnr = stamnr;
    }

    public String getLesOfProject() {
        return lesOfProject;
    }

    public void setLesOfProject(String lesOfProject) {
        this.lesOfProject = lesOfProject;
    }
}
