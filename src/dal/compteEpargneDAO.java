package dal;

import bo.Agence;
import bo.CompteEpargnes;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class compteEpargneDAO implements IDAO<Long, CompteEpargnes> {
    // region variable
    agenceDAO agenceDAO = new agenceDAO();
    private static final String INSERT_QUERY = "INSERT INTO comptes (identifiant, taux_interet, id_agence) VALUES(?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE comptes SET identifiant = ?, taux_interet = ?, id_agence = ?, solde = ? WHERE id = ?";
    private static final String REMOVE_QUERY = "DELETE FROM comptes WHERE id = ?";
    private static final String FIND_QUERY = "SELECT * FROM comptes WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM comptes";
    //endregion

    //region méthodes
    @Override
    public void create(CompteEpargnes ce) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection
                    .prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS ) ) {
                ps.setInt( 1, ce.getIdentifiant() );
                ps.setInt( 2, ce.getTauxInteret());
                ps.setInt(3, ce.getAgence().getId());
                ps.executeUpdate();
                try ( ResultSet rs = ps.getGeneratedKeys() ) {
                    if ( rs.next() ) {
                        ce.setId( rs.getInt( 1 ) );
                    }
                }
            }
        }
    }

    @Override
    public void update(CompteEpargnes ce) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( UPDATE_QUERY ) ) {
                ps.setInt( 1, ce.getIdentifiant());
                ps.setInt( 2, ce.getTauxInteret() );
                ps.setInt( 3, ce.getId() );
                ps.setDouble(4, ce.getSold());
                ps.setInt(5, ce.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void remove(CompteEpargnes ce) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( REMOVE_QUERY ) ) {
                ps.setInt( 1, ce.getId() );
                ps.executeUpdate();
            }
        }
    }

    @Override
    public CompteEpargnes findById(Long aLong) throws SQLException, IOException, ClassNotFoundException {
        CompteEpargnes ce = null;
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( FIND_QUERY ) ) {
                ps.setLong( 1, aLong );
                try ( ResultSet rs = ps.executeQuery() ) {
                    if ( rs.next() ) {
                        ce = new CompteEpargnes();
                        ce.setId( rs.getInt( "id" ) );
                        ce.setIdentifiant(rs.getInt("identifiant"));
                        ce.setAgence(agenceDAO.findById((long)rs.getInt( "id_agence" )));
                        ce.setTauxInteret( rs.getInt( "taux_interet" ) );
                    }
                }
            }
        }
        return ce;
    }

    @Override
    public List<CompteEpargnes> findAll() throws SQLException, IOException, ClassNotFoundException {
        List<CompteEpargnes> list = new ArrayList<>();
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( FIND_ALL_QUERY ) ) {
                try ( ResultSet rs = ps.executeQuery() ) {
                    while ( rs.next() ) {
                        CompteEpargnes ce = new CompteEpargnes();
                        ce.setId( rs.getInt( "id" ) );
                        ce.setIdentifiant(rs.getInt("identifiant"));
                        ce.setAgence(agenceDAO.findById((long)rs.getInt( "id_agence" )));
                        ce.setTauxInteret( rs.getInt( "taux_interet" ) );
                        list.add( ce );
                    }
                }
            }
        }
        return list;
    }

    //endregion
}
