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
            "e.developer_level, e.english_level, e.team_id, t.team_name FROM feedbacks AS f " +
            "JOIN employees AS e ON f.employee_id = e.id " +
            "JOIN teams AS t ON e.team_id = t.id";
    public static final String SQL_SELECT_FEEDBACK_BY_ID = SQL_SELECT_ALL_FEEDBACKS + " WHERE f.id = ?";
    public static final String SQL_UPDATE_FEEDBACK = "UPDATE feedbacks SET description = ?, feedback_date = ?, " +
            "employee_id = ? WHERE id = ?";
    public static final String SQL_DELETE_FEEDBACK_BY_ID = "DELETE FROM feedbacks WHERE id = ?";

    public FeedbackDAO(Connection connection) {
        super(connection);
    }

    private static PreparedStatement setFeedbackToPreparedStatement(PreparedStatement preparedStatement,
                                                                    Feedback feedback) throws SQLException {
        preparedStatement.setString(1, feedback.getDescription());
        preparedStatement.setDate(2, Date.valueOf(feedback.getFeedbackDate()));
        preparedStatement.setInt(3, feedback.getEmployee().getId());
        return preparedStatement;
    }

    @Override
    public int create(Feedback feedback) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_FEEDBACK);
            result = setFeedbackToPreparedStatement(preparedStatement, feedback).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static Feedback initFeedback(ResultSet resultSet, Feedback feedback, String idColumnName) throws SQLException {
        feedback.setId(resultSet.getInt(idColumnName));
        feedback.setDescription(resultSet.getString("description"));
        feedback.setFeedbackDate(resultSet.getDate("feedback_date").toLocalDate());
        feedback.setEmployee(EmployeeDAO.initEmployee(resultSet, new Employee(), "employee_id"));
        return feedback;
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FEEDBACKS);
            feedbacks = new ArrayList<>();
            while (resultSet.next()) {
                feedbacks.add(initFeedback(resultSet, new Feedback(), "id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }

    @Override
    public Feedback findEntityById(int id) {
        Feedback feedback = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_FEEDBACK_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                feedback = initFeedback(resultSet, new Feedback(),"id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedback;
    }

    @Override
    public int update(Feedback feedback) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_FEEDBACK);
            preparedStatement = setFeedbackToPreparedStatement(preparedStatement, feedback);
            preparedStatement.setInt(4, feedback.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_FEEDBACK_BY_ID);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
