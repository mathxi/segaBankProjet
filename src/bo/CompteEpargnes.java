package bo;

import java.util.Scanner;

public class CompteEpargnes extends Compte{

    //region variables
    private int tauxInteret;
    private static Scanner sc = new Scanner( System.in );
    // endregion

    // region constructeur
    public CompteEpargnes(){

    }

    public CompteEpargnes(int tauxInteret) {
        super();
        this.tauxInteret = tauxInteret;
    }
    //endregion

    // region getter/setter
    public int getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(int tauxInteret) {
        this.tauxInteret = tauxInteret;
    }
    //endregion

    //region méthodes
    public double calculIntérêt(){

        return sold;
    }
    //endregion

}
