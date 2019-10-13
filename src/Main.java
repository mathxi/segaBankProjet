import bo.*;
import controller.menuController;
import dal.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static agenceDAO agenceDAO = new agenceDAO();
    private static Scanner sc = new Scanner( System.in );
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        dspMainMenu();
    }

    public static void dspMainMenu() throws SQLException, IOException, ClassNotFoundException {

        int response;
        boolean first = true;
        do {
            if ( !first ) {
                System.out.println( "Mauvais choix... merci de recommencer !" );
            }
            System.out.println( "======================================" );
            System.out.println( "=========== MENU - CONTACTS ==========" );
            System.out.println( "======================================" );
            System.out.println( "1 - Ajouter une nouvelle agence" );
            System.out.println( "2 - Créer un compte épargne" );
            System.out.println( "3 - Créer un compte payant" );
            System.out.println( "4 - Créer un compte simple" );
            System.out.println( "5 - Effectuer un retrait" );
            System.out.println( "6 - Effectuer un virement" );
            System.out.println("7 - Sortir");
            System.out.print( "Entrez votre choix : " );
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while ( response < 1 || response > 9 );

        switch ( response ) {
            case 1:
                createAgence();
                break;
            case 2:
               CreateCE();
                break;
            case 3:
                CreateCP();
                break;
            case 4:
                CreateCS();
                break;
            case 5:
                RetraitCompte();
                break;
            case 6:
                VersementCompte();
                break;
            case 7:
                first = false;
                break;
        }
    }



    public static void createAgence() throws SQLException, IOException, ClassNotFoundException {

        System.out.println( "======================================" );
        System.out.println( "======== CREATION D'UNE AGENCE =======" );
        System.out.println( "======================================" );
        Agence agence = new Agence();
        System.out.print( "Entrez le code de l'agence" );
        agence.setCode( sc.nextLine() );
        System.out.print( "Entrez l'adresse de l'agence" );
        agence.setAdresse( sc.nextLine() );
        menuController.persistAgence(agence);
        System.out.println( "Contact créé avec succès!" );
        dspMainMenu();

    }

    public static void CreateCE() throws SQLException, IOException, ClassNotFoundException {

        System.out.println( "======================================" );
        System.out.println( "==== CREATION D'UN COMPTE EPARGNE ====" );
        System.out.println( "======================================" );
        CompteEpargnes ce = new CompteEpargnes();
        System.out.println("Entrez l'identifiant du compte");
        ce.setIdentifiant(sc.nextInt());
        System.out.print( "Entrez le taux d'interet du compte" );
        ce.setTauxInteret(sc.nextInt());
        System.out.println("Entrez l'agence rattrachée au compte parmis les agences suivante");
        ShowAllCompte();
        ce.setAgence(SelectCompte(sc.nextLong()));
        menuController.peristCompteEpargne(ce);
        System.out.println( "Contact créé avec succès!" );
        dspMainMenu();
    }

    public static void CreateCP() throws SQLException, IOException, ClassNotFoundException {
        System.out.println( "======================================" );
        System.out.println( "==== CREATION D'UN COMPTE PAYANT =====" );
        System.out.println( "======================================" );
        ComptePayants cp = new ComptePayants();
        System.out.println("Entrez l'identifiant du compte");
        cp.setIdentifiant(sc.nextInt());
        System.out.print( "Entrez le taux de taxes du compte" );
        cp.setTauxTaxes(sc.nextInt());
        System.out.println("Entrez l'agence rattrachée au compte parmis les agences suivante");
        ShowAllCompte();
        cp.setAgence(SelectCompte(sc.nextLong()));
        menuController.persistComptePayant(cp);
        System.out.println( "Contact créé avec succès!" );
        dspMainMenu();
    }

    public static void CreateCS() throws SQLException, IOException, ClassNotFoundException {
        System.out.println( "======================================" );
        System.out.println( "==== CREATION D'UN COMPTE PAYANT =====" );
        System.out.println( "======================================" );
        CompteSimples cs = new CompteSimples();
        System.out.println("Entrez l'identifiant du compte");
        cs.setIdentifiant(sc.nextInt());
        System.out.print( "Entrez le decouvert maximum" );
        cs.setDecouvert(sc.nextInt());
        System.out.println("Entrez l'agence rattrachée au compte parmis les agences suivante");
        ShowAllCompte();
        cs.setAgence(SelectCompte(sc.nextLong()));
        menuController.persistCompteSimple(cs);
        System.out.println( "Contact créé avec succès!" );
        dspMainMenu();
    }

    public static void ShowAllCompte() throws SQLException, IOException, ClassNotFoundException {

        List<Agence> lAgence = new ArrayList<Agence>();
        lAgence = agenceDAO.findAll();
        for(int i = 0; i<lAgence.size(); i++) {
            System.out.println(lAgence.get(i).getId() + "-" + lAgence.get(i).getCode());
        }

    }

    public static Agence SelectCompte(long id) throws SQLException, IOException, ClassNotFoundException {
        Agence agenceSelected = new Agence();
        agenceSelected = agenceDAO.findById(id);
        return agenceSelected;

    }

    public static void RetraitCompte() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Entrez le compte ou il faut retirer l'argent");
        long Id = sc.nextLong();
        System.out.println("Entrez la somme a retirer de ce compte");
        double valeur = sc.nextInt();
        compteSimpleDAO csDAO = new compteSimpleDAO();
        CompteSimples cs = csDAO.findById(Id);

        comptePayantDAO cpDAO = new comptePayantDAO();
        ComptePayants cp = cpDAO.findById(Id);

        compteEpargneDAO ceDAO = new compteEpargneDAO();
        CompteEpargnes ce = ceDAO.findById(Id);

        if(cs != null){
            cs.retrait(valeur);
            csDAO.update(cs);
        }else if(cp != null){
            cp.retrait(valeur);
            cpDAO.update(cp);
        }else {
            ce.retrait(valeur);
            ceDAO.update(ce);
        }

        System.out.println("Vous venez de retirer " + valeur + "€ de votre compte !");

        dspMainMenu();
    }

    public static void VersementCompte() throws SQLException, IOException, ClassNotFoundException{
        System.out.println("Entrez le compte ou il faut verser l'argent");
        long Id = sc.nextLong();
        System.out.println("Entrez la somme a retirer de ce compte");
        double valeur = sc.nextInt();
        compteSimpleDAO csDAO = new compteSimpleDAO();
        CompteSimples cs = csDAO.findById(Id);

        comptePayantDAO cpDAO = new comptePayantDAO();
        ComptePayants cp = cpDAO.findById(Id);

        compteEpargneDAO ceDAO = new compteEpargneDAO();
        CompteEpargnes ce = ceDAO.findById(Id);

        if(cs != null){
            cs.versement(valeur);
            csDAO.update(cs);
        }else if(cp != null){
            cp.versement(valeur);
            cpDAO.update(cp);
        }else {
            ce.versement(valeur);
            ceDAO.update(ce);
        }
        dspMainMenu();
    }

}
