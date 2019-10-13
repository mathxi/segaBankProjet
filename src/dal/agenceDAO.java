package dal;


import bo.Agence;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class agenceDAO implements IDAO<Long, Agence>  {
    //region variables
    private static final String INSERT_QUERY = "INSERT INTO agence (code, adresse) VALUES(?,?)";
    private static final String UPDATE_QUERY = "UPDATE agence SET code = ?, adresse = ? WHERE id = ?";
    private static final String REMOVE_QUERY = "DELETE FROM agence WHERE id = ?";
    private static final String FIND_QUERY = "SELECT * FROM agence WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM agence";
    // endregion

    // region méthodes
    @Override
    public void create(Agence agence) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection
                    .prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS ) ) {
                ps.setString( 1, agence.getCode() );
                ps.setString( 2, agence.getAdresse());
                ps.executeUpdate();
                try ( ResultSet rs = ps.getGeneratedKeys() ) {
                    if ( rs.next() ) {
                        agence.setId( rs.getInt( 1 ) );
                    }
                }
            }
        }
    }

    @Override
    public void update(Agence agence) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( UPDATE_QUERY ) ) {
                ps.setString( 1, agence.getCode() );
                ps.setString( 2, agence.getAdresse() );
                ps.setInt( 3, agence.getId() );
                ps.executeUpdate();
            }
        }
    }

    @Override
    public void remove(Agence agence) throws SQLException, IOException, ClassNotFoundException {

        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( REMOVE_QUERY ) ) {
                ps.setInt( 1, agence.getId() );
                ps.executeUpdate();
            }
        }

    }

    @Override
    public Agence findById(Long aLong) throws SQLException, IOException, ClassNotFoundException {
        Agence agence = null;
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( FIND_QUERY ) ) {
                ps.setLong( 1, aLong );
                try ( ResultSet rs = ps.executeQuery() ) {
                    if ( rs.next() ) {
                        agence = new Agence();
                        agence.setId( rs.getInt( "id" ) );
                        agence.setCode( rs.getString( "code" ));
                        agence.setAdresse( rs.getString( "adresse" ) );
                    }
                }
            }
        }
        return agence;
    }

    @Override
    public List<Agence> findAll() throws SQLException, IOException, ClassNotFoundException {
        List<Agence> list = new ArrayList<>();
        Connection connection = PersistenceManager.getConnection();
        if ( connection != null ) {
            try ( PreparedStatement ps = connection.prepareStatement( FIND_ALL_QUERY ) ) {
                try ( ResultSet rs = ps.executeQuery() ) {
                    while ( rs.next() ) {
                        Agence agence = new Agence();
                        agence.setId( rs.getInt( "id" ) );
                        agence.setCode( rs.getString( "code" ) );
                        agence.setCode( rs.getString( "adresse" ) );
                        list.add( agence );
                    }
                }
            }
        }
        return list;
    }

    //endregion

}