package dal;

import bo.CompteSimples;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class compteSimpleDAO implements IDAO<Long, CompteSimples> {

    //region variable
    agenceDAO agenceDAO = new agenceDAO();
    private static final String INSERT_QUERY = "INSERT INTO comptes (identifiant, decouvert, id_agence) VALUES(?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE comptes SET identifiant = ?, decouvert = ?, id_agence = ?, solde = ? WHERE id = ?";
    private static final String REMOVE_QUERY = "DELETE FROM comptes WHERE id = ?";
    private static final String FIND_QUERY = "SELECT * FROM comptes WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM comptes";
    //endregion

    //region méthodes
    @Override
    public void create(CompteSimples cs) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection
                    .prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS ) ) {
                ps.setInt( 1, cs.getIdentifiant() );
                ps.setInt( 2, cs.getDecouvert());
                ps.setInt(3, cs.getAgence().getId());
                ps.executeUpdate();
                try ( ResultSet rs = ps.getGeneratedKeys() ) {
                    if ( rs.next() ) {
                        cs.setId( rs.getInt( 1 ) );
                    }
                }
            }
        }
    }

    @Override
    public void update(CompteSimples cs) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( UPDATE_QUERY ) ) {
                ps.setInt( 1, cs.getIdentifiant());
                ps.setInt( 2, cs.getDecouvert() );
                ps.setInt( 3, cs.getId() );
                ps.setDouble(4, cs.getSold());
                ps.setInt(5, cs.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void remove(CompteSimples cs) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( REMOVE_QUERY ) ) {
                ps.setInt( 1, cs.getId() );
                ps.executeUpdate();
            }
        }
    }

    @Override
    public CompteSimples findById(Long l) throws SQLException, IOException, ClassNotFoundException {
        CompteSimples cs = null;
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( FIND_QUERY ) ) {
                ps.setLong( 1, l );
                try ( ResultSet rs = ps.executeQuery() ) {
                    if ( rs.next() ) {
                        cs = new CompteSimples();
                        cs.setId( rs.getInt( "id" ) );
                        cs.setIdentifiant(rs.getInt("identifiant"));
                        cs.setAgence(agenceDAO.findById((long)rs.getInt( "id_agence" )));
                        cs.setDecouvert( rs.getInt( "decouvert" ) );
                    }
                }
            }
        }
        return cs;
    }

    @Override
    public List<CompteSimples> findAll() throws SQLException, IOException, ClassNotFoundException {
        List<CompteSimples> list = new ArrayList<>();
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( FIND_ALL_QUERY ) ) {
                try ( ResultSet rs = ps.executeQuery() ) {
                    while ( rs.next() ) {
                        CompteSimples cs = new CompteSimples();
                        cs.setId( rs.getInt( "id" ) );
                        cs.setIdentifiant(rs.getInt("identifiant"));
                        cs.setAgence(agenceDAO.findById((long)rs.getInt( "id_agence" )));
                        cs.setDecouvert( rs.getInt( "taux_taxes" ) );
                        list.add( cs );
                    }
                }
            }
        }
        return list;
    }
    //endregion

}
