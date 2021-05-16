package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Team;
import by.andersen.intensive4.jdbc.connector.ConnectorDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamDAO extends EntityDAO<Team> {
    public static final String SQL_INSERT_TEAM = "INSERT INTO teams (team_name) VALUES (?)";
    public static final String SQL_SELECT_ALL_TEAMS = "SELECT * FROM teams";
    public static final String SQL_SELECT_TEAM_BY_ID = SQL_SELECT_ALL_TEAMS + " WHERE id = ?";
    public static final String SQL_UPDATE_TEAM = "UPDATE teams SET team_name = ? WHERE id = ?";
    public static final String SQL_DELETE_TEAM_BY_ID = "DELETE FROM teams WHERE id = ?";

    @Autowired
    public TeamDAO(ConnectorDB connectorDB) {
        super(connectorDB);
    }

    @Override
    public int create(Team team) {
        int result = 0;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_TEAM);
            preparedStatement.setString(1, team.getTeamName());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return result;
    }

    protected static Team initTeam(ResultSet resultSet, Team team, String idColumnName) throws SQLException {
        team.setId(resultSet.getInt(idColumnName));
        team.setTeamName(resultSet.getString("team_name"));
        return team;
    }

    @Override
    public List<Team> findAll() {
        List<Team> teams = null;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TEAMS);
            teams = new ArrayList<>();
            while (resultSet.next()) {
                teams.add(initTeam(resultSet, new Team(), "id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return teams;
    }

    @Override
    public Team findById(int id) {
        Team team = null;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TEAM_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                team = initTeam(resultSet, new Team(), "id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return team;
    }

    @Override
    public int update(Team team) {
        int result = 0;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TEAM);
            preparedStatement.setString(1, team.getTeamName());
            preparedStatement.setInt(2, team.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_TEAM_BY_ID);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return result;
    }
}
