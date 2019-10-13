package bo;

public class ComptePayants extends Compte{

    //region variables
    protected int tauxTaxes;
    // endregion

    //region getter/setter
    public int getTauxTaxes() {
        return tauxTaxes;
    }

    public void setTauxTaxes(int tauxTaxes) {
        this.tauxTaxes = tauxTaxes;
    }
    //endregion

    //region méthodes
    @Override
    public boolean versement(double value) {
        this.sold = this.sold + value;
        return true;
    }
    private double calcPourcent(double value){
        return (value*this.tauxTaxes)/100;
    }
    @Override
    public boolean retrait(double value) {
        if(((this.sold - value) +calcPourcent(value)) < 0){
            return false;
        }else{
            this.sold = this.sold -  (value + calcPourcent(value));
            return true;
        }
    }
    //endregion
}