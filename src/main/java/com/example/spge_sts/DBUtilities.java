package com.example.spge_sts;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBUtilities {

    private DBUtilities() {
    }

    private static final String dbUrl = "jdbc:mysql://localhost:3306/spge_sts_2";
    private static final String dbUser = "root";
    private static final String dbPassword = "bocanito";

    protected static String suchUserExists(String username, String password) {
        String sqlQuery = "SELECT UserID FROM users WHERE Username = '" + username + "' AND Password = '" + password + "';";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Map<String, String> getUserData(String ID) {
        Map<String, String> data = new HashMap<>();
        String sqlQuery = "SELECT * from users WHERE UserID = " + ID + ";";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                data.put("UserID", resultSet.getString(1));
                data.put("Username", resultSet.getString(2));
                data.put("Password", resultSet.getString(3));
                data.put("Email", resultSet.getString(4));
                data.put("Phone", resultSet.getString(5));
                data.put("FirstName", resultSet.getString(6));
                data.put("LastName", resultSet.getString(7));
                data.put("UserRole", resultSet.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    protected static boolean createUser(String username, String password, String email, String phone, String firstName, String lastName, String userRole) {
        String sqlQuery = "INSERT INTO users (Username, Password, Email, Phone, FirstName, LastName, UserRole) VALUES (?, ?, ?, ?, ?, ?, ?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, firstName);
            preparedStatement.setString(6, lastName);
            preparedStatement.setString(7, userRole);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static boolean deleteRecord(String ID) {
        String sqlQuery = "DELETE FROM users WHERE UserID = " + ID + ";";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static boolean updateRecord(String string, String ID) {
        String sqlQuery = getUpdateSqlQuery(string, ID);
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static int getCountOfUsers() {
        String sqlQuery = "SELECT COUNT(*) FROM users;";
        int count = -1;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // DOES NOT EQUAL getUserData(ID), because index != ID
    protected static Map<String, String> getIdUsernameRoleByIndex(int index) {
        Map<String, String> specificData = new HashMap<>();
        String sqlQuery = "SELECT * FROM users";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int recordCount = 0;
            while (resultSet.next()) {
                recordCount++;
                if (recordCount == index) {
                    String userID = resultSet.getString("UserID");
                    String username = resultSet.getString("Username");
                    String userRole = resultSet.getString("UserRole");
                    specificData.put("UserID", userID);
                    specificData.put("Username", username);
                    specificData.put("UserRole", userRole);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specificData;
    }

    private static String getUpdateSqlQuery(String string, String ID) {
        String sqlQuery = null;
        if (string.matches(ValidationRegexes.TEACHER_USERNAME.getRegex()) || string.matches(ValidationRegexes.STUDENT_USERNAME.getRegex())) {
            String firstName = String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1, string.indexOf('_'));
            String lastNameFirstCharacter = String.valueOf(string.charAt(string.indexOf('_') + 1));
            if (string.matches(ValidationRegexes.TEACHER_USERNAME.getRegex())) {
                String lastName = lastNameFirstCharacter.toUpperCase() + string.substring(string.indexOf('_') + 2, string.indexOf('@'));
                sqlQuery = "UPDATE users SET Username = '" + string + "', FirstName = '" + firstName + "', LastName = '" + lastName + "' WHERE UserID = '" + ID + "';";
            } else if (string.matches(ValidationRegexes.STUDENT_USERNAME.getRegex())) {
                String lastName = lastNameFirstCharacter.toUpperCase() + string.substring(string.indexOf('_') + 2, string.lastIndexOf('_'));
                sqlQuery = "UPDATE users SET Username = '" + string + "', FirstName = '" + firstName + "', LastName = '" + lastName + "' WHERE UserID = '" + ID + "';";
            }
        } else if (string.matches(ValidationRegexes.LAST_NAME.getRegex())) {
            String username = getUserData(ID).get("Username");
            String updatedUsername;
            if (username.matches(ValidationRegexes.TEACHER_USERNAME.getRegex())) {
                updatedUsername = username.substring(0, username.indexOf('_') + 1) + string.toLowerCase() + username.substring(username.indexOf('@'));
            } else {
                updatedUsername = username.substring(0, username.indexOf('_') + 1) + string.toLowerCase() + username.substring(username.lastIndexOf('_'));
            }
            sqlQuery = "UPDATE users SET LastName = '" + string + "', Username = '" + updatedUsername + "' WHERE UserID = '" + ID + "';";
        } else if (string.matches(ValidationRegexes.FIRST_NAME.getRegex())) {
            String username = getUserData(ID).get("Username");
            String updatedUsername = string.toLowerCase() + username.substring(username.indexOf('_'));
            sqlQuery = "UPDATE users SET FirstName = '" + string + "', Username = '" + updatedUsername + "' WHERE UserID = '" + ID + "';";
        } else if (string.matches(ValidationRegexes.EMAIL.getRegex())) {
            sqlQuery = "UPDATE users SET Email = '" + string + "' WHERE UserID = '" + ID + "';";
        } else if (string.matches(ValidationRegexes.PHONE.getRegex())) {
            sqlQuery = "UPDATE users SET Phone = '" + string + "' WHERE UserID = '" + ID + "';";
        }
        return sqlQuery;
    }

    protected static boolean createTest(String testName, String description, String code, String duration, String passingScore, String questions, String dateCreated, String dateUpdated, String creatorUserID, String status) {
        String sqlQuery = "INSERT INTO tests (TestName, Description, Code, DurationMinutes, PassingScore, Questions, DateCreated, DateUpdated, CreatorUserID, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, testName);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, code);
            preparedStatement.setString(4, duration);
            preparedStatement.setString(5, passingScore);
            preparedStatement.setString(6, questions);
            preparedStatement.setString(7, dateCreated);
            preparedStatement.setString(8, dateUpdated);
            preparedStatement.setString(9, creatorUserID);
            preparedStatement.setString(10, status);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static ArrayList<String> getUsersIdsBy(String criterion, String string) {
        ArrayList<String> IDs = new ArrayList<>();
        String query;
        if (criterion.equals("UserID")) {
            query = "SELECT UserID FROM users WHERE UserID LIKE '%" + string + "%';";
            System.out.println("UserID criterion");
        } else if (criterion.equals("Username")) {
            query = "SELECT UserID FROM users WHERE Username LIKE '%" + string + "%';";
            System.out.println("Username criterion");
        } else {
            query = "SELECT UserID FROM users WHERE UserRole LIKE '%" + string + "%';";
            System.out.println("UserRole criterion");
        }
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                IDs.add(resultSet.getString("UserID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IDs;
    }

    protected static String getLastInsertedTestID() {
        String lastTestID = null;
        String lastTestQuestions = null;
        String query = "SELECT TestID, Questions FROM tests ORDER BY tests.TestID DESC LIMIT 1;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lastTestID = resultSet.getString("TestID");
                lastTestQuestions = resultSet.getString("Questions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastTestID + " " + lastTestQuestions;
    }

    protected static boolean createQuestion(String testID, String questionText, String questionType, String answers, String correctAnswer, String points, String number) {
        String sqlQuery = "INSERT INTO questions (TestID, QuestionText, QuestionType, Answers, CorrectAnswer, Points, Number) VALUES (?, ? ,? ,? ,? ,?, ?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, testID);
            preparedStatement.setString(2, questionText);
            preparedStatement.setString(3, questionType);
            preparedStatement.setString(4, answers);
            preparedStatement.setString(5, correctAnswer);
            preparedStatement.setString(6, points);
            preparedStatement.setString(7, number);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static ArrayList<String> getQuestionIDs(String code) {
        ArrayList<String> questionIDs = new ArrayList<>();
        String sqlQuery = "SELECT QuestionID FROM questions INNER JOIN tests ON questions.TestID = tests.TestID WHERE Code = '" + code + "';";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                questionIDs.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionIDs;
    }

    protected static Map<String, String> getQuestionDataByID(String ID) {
        Map<String, String> questionData = new HashMap<>();
        String sqlQuery = "SELECT * FROM questions WHERE QuestionID = '" + ID + "';";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                questionData.put("QuestionID", resultSet.getString(1));
                questionData.put("Number", resultSet.getString(8));
                questionData.put("Points", resultSet.getString(7));
                questionData.put("QuestionText", resultSet.getString(3));
                questionData.put("QuestionType", resultSet.getString(4));
                questionData.put("Answers", resultSet.getString(5));
                questionData.put("CorrectAnswer", resultSet.getString(6));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionData;
    }

    protected static boolean createResponse(String QuestionID, String UserID, String responseText, String SelectedOptions, String isCorrect, String submissionTimestamp) {
        String sqlQuery = "INSERT INTO responses (QuestionID, UserID, ResponseText, SelectedOptions, IsCorrect, SubmissionTimestamp) VALUES (?, ? ,? ,? ,? ,?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, QuestionID);
            preparedStatement.setString(2, UserID);
            preparedStatement.setString(3, responseText);
            preparedStatement.setString(4, SelectedOptions);
            preparedStatement.setString(5, isCorrect);
            preparedStatement.setString(6, submissionTimestamp);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}


