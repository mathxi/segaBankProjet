package controller;

import bo.Agence;
import bo.CompteEpargnes;
import bo.ComptePayants;
import bo.CompteSimples;
import dal.*;

import java.io.IOException;
import java.sql.SQLException;

public class menuController {

    public static void persistAgence(Agence agence) throws SQLException, IOException, ClassNotFoundException {
        agenceDAO agenceDB = new agenceDAO();
        agenceDB.create(agence);
    }

    public static void peristCompteEpargne(CompteEpargnes ce) throws SQLException, IOException, ClassNotFoundException {
        compteEpargneDAO ceDAO = new compteEpargneDAO();
        ceDAO.create(ce);
    }

    public static void persistComptePayant(ComptePayants cp) throws SQLException, IOException, ClassNotFoundException {
        comptePayantDAO cpDAO = new comptePayantDAO();
        cpDAO.create(cp);
    }

    public static void persistCompteSimple(CompteSimples cs) throws SQLException, IOException, ClassNotFoundException {
        compteSimpleDAO csDAO = new compteSimpleDAO();
        csDAO.create(cs);
    }
}
