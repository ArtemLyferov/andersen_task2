package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO extends AbstractDAO<Feedback> {

    public static final String SQL_INSERT_FEEDBACK = "INSERT INTO feedbacks (description, feedback_date, employee_id) " +
            "VALUES (?, ?, ?)";
    public static final String SQL_SELECT_ALL_FEEDBACKS = "SELECT f.id, f.description, f.feedback_date, f.employee_id, " +
            "e.surname, e.name, e.patronymic, e.dob, e.email, e.skype, e.phone_number, e.employment_date, e.experience, " +
            "e.developer_level, e.english_level, e.team_id, t.name_team FROM feedbacks AS f " +
            "JOIN employees AS e ON f.employee_id = e.id " +
            "JOIN teams AS t ON e.team_id = t.id";
    public static final String SQL_SELECT_FEEDBACK_BY_ID = SQL_SELECT_ALL_FEEDBACKS + " WHERE f.id = ?";
    public static final String SQL_UPDATE_FEEDBACK = "UPDATE feedbacks SET description = ?, feedback_date = ?, " +
            "employee_id = ? WHERE id = ?";
    public static final String SQL_DELETE_FEEDBACK_BY_ID = "DELETE FROM feedbacks WHERE id = ?";

    public FeedbackDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Feedback feedback) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_FEEDBACK);
            preparedStatement.setString(1, feedback.getDescription());
            preparedStatement.setDate(2, new Date(feedback.getFeedbackDate().getTime()));
            preparedStatement.setInt(3, feedback.getEmployee().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FEEDBACKS);
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(resultSet.getInt("id"));
                feedback.setDescription(resultSet.getString("description"));
                Employee employee = new Employee();
                feedback.setEmployee(EmployeeDAO.initEmployee(resultSet, employee));
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }

    @Override
    public Feedback findEntityById(int id) {
        Feedback feedback = new Feedback();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_FEEDBACK_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                feedback.setId(resultSet.getInt("id"));
                feedback.setDescription(resultSet.getString("description"));
                Employee employee = new Employee();
                feedback.setEmployee(EmployeeDAO.initEmployee(resultSet, employee));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedback;
    }

    @Override
    public void update(Feedback feedback) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_FEEDBACK);
            preparedStatement.setString(1, feedback.getDescription());
            preparedStatement.setDate(2, new Date(feedback.getFeedbackDate().getTime()));
            preparedStatement.setInt(3, feedback.getEmployee().getId());
            preparedStatement.setInt(4, feedback.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_FEEDBACK_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
