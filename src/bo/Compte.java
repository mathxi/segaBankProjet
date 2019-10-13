package bo;

abstract class Compte {
    // region variables
    protected int id;
    protected int identifiant;
    protected double sold;
    protected Agence agence;

    // endregion

    //region getter/setter

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence compte) {
        this.agence = compte;
    }

    //endregion

    // region Constructeur

    public Compte() {
        this.sold = 0.00;
    }

    // endregion

    // region méthodes

    public boolean versement(double value){
            this.sold = this.sold + value;
            return true;
    };
    public boolean retrait(double value){
        if((this.sold - value) < 0){
            return false;
        }else{
            this.sold = this.sold - value;
            return true;
        }

    };

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Compte");
        sb.append(" id=").append(id);
        sb.append(",sold=").append(sold);
        return sb.toString();
    }

    // endregion

}
