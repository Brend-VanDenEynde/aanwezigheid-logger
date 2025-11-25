package brend.vandeneynde.aanwezigheidlogger.model;

public class Student {
    private int id;
    private String departement;
    private String email;
    private boolean heeftfoto;
    private String mifare;
    private String naam;
    private String opleiding;
    private int p_persoon;
    private boolean printed;
    private String schooljaar;
    private String stamnr;
    private String voornaam;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHeeftfoto() {
        return heeftfoto;
    }

    public void setHeeftfoto(boolean heeftfoto) {
        this.heeftfoto = heeftfoto;
    }

    public String getMifare() {
        return mifare;
    }

    public void setMifare(String mifare) {
        this.mifare = mifare;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOpleiding() {
        return opleiding;
    }

    public void setOpleiding(String opleiding) {
        this.opleiding = opleiding;
    }

    public int getP_persoon() {
        return p_persoon;
    }

    public void setP_persoon(int p_persoon) {
        this.p_persoon = p_persoon;
    }

    public boolean isPrinted() {
        return printed;
    }

    public void setPrinted(boolean printed) {
        this.printed = printed;
    }

    public String getSchooljaar() {
        return schooljaar;
    }

    public void setSchooljaar(String schooljaar) {
        this.schooljaar = schooljaar;
    }

    public String getStamnr() {
        return stamnr;
    }

    public void setStamnr(String stamnr) {
        this.stamnr = stamnr;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }
}
