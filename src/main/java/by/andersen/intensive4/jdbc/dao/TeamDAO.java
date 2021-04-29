package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO extends AbstractDAO<Team> {
    public static final String SQL_INSERT_TEAM = "INSERT INTO teams (name_team) VALUES (?)";
    public static final String SQL_SELECT_ALL_TEAMS = "SELECT * FROM teams";
    public static final String SQL_SELECT_TEAM_BY_ID = SQL_SELECT_ALL_TEAMS + " WHERE id = ?";
    public static final String SQL_UPDATE_TEAM = "UPDATE teams SET name_team = ? WHERE id = ?";
    public static final String SQL_DELETE_TEAM_BY_ID = "DELETE FROM teams WHERE id = ?";

    public TeamDAO(Connection connection) {
        super(connection);
    }


    @Override
    public void create(Team team) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_TEAM);
            preparedStatement.setString(1,team.getNameTeam());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TEAMS);
            while (resultSet.next()) {
                Team team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setNameTeam(resultSet.getString("team_name"));
                teams.add(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    @Override
    public Team findEntityById(int id) {
        Team team = new Team();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TEAM_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                team.setId(resultSet.getInt("id"));
                team.setNameTeam(resultSet.getString("team_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    @Override
    public void update(Team team) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TEAM);
            preparedStatement.setString(1, team.getNameTeam());
            preparedStatement.setInt(2, team.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_TEAM_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
