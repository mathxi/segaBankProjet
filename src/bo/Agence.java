package bo;

import java.util.List;
import java.util.Scanner;

public class Agence {

    // region variable
    private int id;
    private String code;
    private String adresse;
    private List<Compte> Comptes;
    private static Scanner sc = new Scanner( System.in );
    // endregion

    // region constructeur
    public Agence(int id, String code, String adresse, List<Compte> comptes) {
        this.id = id;
        this.code = code;
        this.adresse = adresse;
        Comptes = comptes;
    }

    public Agence(String code, String adresse) {
        this.code = code;
        this.adresse = adresse;
    }

    public Agence() {
    }
    //endregion

    //region getter/setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Compte> getComptes() {
        return Comptes;
    }

    public void setComptes(List<Compte> comptes) {
        Comptes = comptes;
    }
    //endregion

}
