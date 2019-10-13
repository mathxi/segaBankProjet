package dal;

import bo.ComptePayants;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class comptePayantDAO implements IDAO<Long, ComptePayants> {

    //region variable
    agenceDAO agenceDAO = new agenceDAO();
    private static final String INSERT_QUERY = "INSERT INTO comptes (identifiant, taux_taxes, id_agence) VALUES(?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE comptes SET identifiant = ?, taux_taxes = ?, id_agence = ?, solde = ? WHERE id = ?";
    private static final String REMOVE_QUERY = "DELETE FROM comptes WHERE id = ?";
    private static final String FIND_QUERY = "SELECT * FROM comptes WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM comptes";
    //endregion

    //region méthodes
    @Override
    public void create(ComptePayants cp) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection
                    .prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS ) ) {
                ps.setInt( 1, cp.getIdentifiant() );
                ps.setInt( 2, cp.getTauxTaxes());
                ps.setInt(3, cp.getAgence().getId());
                ps.executeUpdate();
                try ( ResultSet rs = ps.getGeneratedKeys() ) {
                    if ( rs.next() ) {
                        cp.setId( rs.getInt( 1 ) );
                    }
                }
            }
        }
    }

    @Override
    public void update(ComptePayants cp) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( UPDATE_QUERY ) ) {
                ps.setInt( 1, cp.getIdentifiant());
                ps.setInt( 2, cp.getTauxTaxes() );
                ps.setInt( 3, cp.getId() );
                ps.setDouble(4, cp.getSold());
                ps.setInt(5, cp.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void remove(ComptePayants cp) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( REMOVE_QUERY ) ) {
                ps.setInt( 1, cp.getId() );
                ps.executeUpdate();
            }
        }
    }

    @Override
    public ComptePayants findById(Long aLong) throws SQLException, IOException, ClassNotFoundException {
        ComptePayants cp = null;
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( FIND_QUERY ) ) {
                ps.setLong( 1,aLong);
                try ( ResultSet rs = ps.executeQuery() ) {
                    if ( rs.next() ) {
                        cp = new ComptePayants();
                        cp.setId( rs.getInt( "id" ) );
                        cp.setIdentifiant(rs.getInt("identifiant"));
                        cp.setAgence(agenceDAO.findById((long)rs.getInt( "id_agence" )));
                        cp.setTauxTaxes( rs.getInt( "taux_taxes" ) );
                    }
                }
            }
        }
        return cp;
    }

    @Override
    public List<ComptePayants> findAll() throws SQLException, IOException, ClassNotFoundException {
        List<ComptePayants> list = new ArrayList<>();
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( FIND_ALL_QUERY ) ) {
                try ( ResultSet rs = ps.executeQuery() ) {
                    while ( rs.next() ) {
                        ComptePayants cp = new ComptePayants();
                        cp.setId( rs.getInt( "id" ) );
                        cp.setIdentifiant(rs.getInt("identifiant"));
                        cp.setAgence(agenceDAO.findById((long)rs.getInt( "id_agence" )));
                        cp.setTauxTaxes( rs.getInt( "taux_taxes" ) );
                        list.add( cp );
                    }
                }
            }
        }
        return list;
    }
    //endregion
}
