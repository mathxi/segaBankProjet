package bo;

public class CompteSimples extends Compte{

    //region variable
    private int decouvert;
    // endregion

    //region getter/setter
    public int getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(int decouvert) {
        this.decouvert = decouvert;
    }
    //endregion

    //region méthodes
    @Override
    public boolean retrait(double value) {
        if((this.sold - value) < (0-this.decouvert)){
            return false;
        }else{
            this.sold = this.sold + value;
            return true;
        }
    }
    //endregion
}
