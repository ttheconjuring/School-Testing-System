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

    // ====================================================================== \\

    /* CREATE queries */

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

    protected static boolean createTest(String testName, String description, String code, String responseTime, String passingScore, String questions, String dateCreated, String dateUpdated, String creatorUserID, String status) {
        String sqlQuery = "INSERT INTO tests (TestName, Description, Code, ResponseTime, PassingScore, Questions, DateCreated, DateUpdated, CreatorUserID, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, testName);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, code);
            preparedStatement.setString(4, responseTime);
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

    // ====================================================================== \\

    /* READ queries*/

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

    protected static Map<String, String> getQuestionData(String ID) {
        Map<String, String> data = new HashMap<>();
        String sqlQuery = "SELECT * FROM questions WHERE QuestionID = '" + ID + "';";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                data.put("QuestionID", resultSet.getString(1));
                data.put("TestID", resultSet.getString(2));
                data.put("QuestionText", resultSet.getString(3));
                data.put("QuestionType", resultSet.getString(4));
                data.put("Answers", resultSet.getString(5));
                data.put("CorrectAnswer", resultSet.getString(6));
                data.put("Points", resultSet.getString(7));
                data.put("Number", resultSet.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    // DOES NOT EQUAL getUserData(ID), because index != ID, cannot be replaced
    protected static Map<String, String> getIdUsernameRoleByIndex(int index) {
        Map<String, String> specificData = new HashMap<>();
        String query = "SELECT UserID, Username, UserRole FROM users";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int recordsCount = 0;
            while (resultSet.next()) {
                recordsCount++;
                if (recordsCount == index) {
                    specificData.put("UserID", resultSet.getString(1));
                    specificData.put("Username", resultSet.getString(2));
                    specificData.put("UserRole", resultSet.getString(3));
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specificData;
    }

    protected static ArrayList<String> getUsersIdsBy(String criterion, String string) {
        ArrayList<String> IDs = new ArrayList<>();
        String query;
        if (criterion.equals("UserID")) {
            query = "SELECT UserID FROM users WHERE UserID LIKE '%" + string + "%';";
        } else if (criterion.equals("Username")) {
            query = "SELECT UserID FROM users WHERE Username LIKE '%" + string + "%';";
        } else {
            query = "SELECT UserID FROM users WHERE UserRole LIKE '%" + string + "%';";
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
        String query = "SELECT TestID, Questions FROM tests ORDER BY TestID DESC LIMIT 1;";
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

    protected static int getPassingScoreOfTestBy(String testID) {
        String query = "SELECT PassingScore FROM tests WHERE TestID = " + testID + ";";
        int passingScore = 0;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString(1) != null) {
                    passingScore = Integer.parseInt(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passingScore;
    }

    protected static int getResponseTimeOfTestBy(String code) {
        String query = "SELECT ResponseTime FROM tests WHERE Code = '" + code + "';";
        int responseTime = 1;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString(1) != null) {
                    responseTime = Integer.parseInt(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responseTime;
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

    // ====================================================================== \\

    /* UPDATE queries*/

    protected static boolean updateUserData(String string, String userID) {
        String query = makeUpdateUserDataQuery(string, userID);
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // ====================================================================== \\

    /* DELETE queries*/

    protected static boolean deleteUser(String userID) {
        String query = "DELETE FROM users WHERE UserID = " + userID + ";";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // ====================================================================== \\

    /* OTHER queries */

    private static String makeUpdateUserDataQuery(String string, String userID) {
        String sqlQuery = null;
        if (string.matches(ValidationRegexes.TEACHER_USERNAME.getRegex()) || string.matches(ValidationRegexes.STUDENT_USERNAME.getRegex())) {
            String firstName = String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1, string.indexOf('_'));
            String lastNameFirstCharacter = String.valueOf(string.charAt(string.indexOf('_') + 1));
            if (string.matches(ValidationRegexes.TEACHER_USERNAME.getRegex())) {
                String lastName = lastNameFirstCharacter.toUpperCase() + string.substring(string.indexOf('_') + 2, string.indexOf('@'));
                sqlQuery = "UPDATE users SET Username = '" + string + "', FirstName = '" + firstName + "', LastName = '" + lastName + "' WHERE UserID = '" + userID + "';";
            } else if (string.matches(ValidationRegexes.STUDENT_USERNAME.getRegex())) {
                String lastName = lastNameFirstCharacter.toUpperCase() + string.substring(string.indexOf('_') + 2, string.lastIndexOf('_'));
                sqlQuery = "UPDATE users SET Username = '" + string + "', FirstName = '" + firstName + "', LastName = '" + lastName + "' WHERE UserID = '" + userID + "';";
            }
        } else if (string.matches(ValidationRegexes.LAST_NAME.getRegex())) {
            String username = getUserData(userID).get("Username");
            String updatedUsername;
            if (username.matches(ValidationRegexes.TEACHER_USERNAME.getRegex())) {
                updatedUsername = username.substring(0, username.indexOf('_') + 1) + string.toLowerCase() + username.substring(username.indexOf('@'));
            } else {
                updatedUsername = username.substring(0, username.indexOf('_') + 1) + string.toLowerCase() + username.substring(username.lastIndexOf('_'));
            }
            sqlQuery = "UPDATE users SET LastName = '" + string + "', Username = '" + updatedUsername + "' WHERE UserID = '" + userID + "';";
        } else if (string.matches(ValidationRegexes.FIRST_NAME.getRegex())) {
            String username = getUserData(userID).get("Username");
            String updatedUsername = string.toLowerCase() + username.substring(username.indexOf('_'));
            sqlQuery = "UPDATE users SET FirstName = '" + string + "', Username = '" + updatedUsername + "' WHERE UserID = '" + userID + "';";
        } else if (string.matches(ValidationRegexes.EMAIL.getRegex())) {
            sqlQuery = "UPDATE users SET Email = '" + string + "' WHERE UserID = '" + userID + "';";
        } else if (string.matches(ValidationRegexes.PHONE.getRegex())) {
            sqlQuery = "UPDATE users SET Phone = '" + string + "' WHERE UserID = '" + userID + "';";
        }
        return sqlQuery;
    }

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

    protected static boolean testIsFree(String code) {
        String query = "SELECT status FROM tests WHERE Code = '" + code + "';";
        boolean flag = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                flag = resultSet.getString(1).equals("free");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    protected static int calculatePointsReceivedByStudent(String userID, String testID) {
        String query = "SELECT SUM(Points) AS TotalPoints FROM responses r INNER JOIN questions q ON r.QuestionID = q.QuestionID INNER JOIN tests t ON q.TestID = t.TestID WHERE r.UserID = " + userID + " AND r.IsCorrect = 'true' AND t.TestID = " + testID + ";";
        int points = 0;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString(1) != null) {
                    points = Integer.parseInt(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }

    protected static int calculateAllPointsOfATest(String testID) {
        String query = "SELECT SUM(Points) AS MaxPoints FROM questions WHERE TestID = " + testID + ";";
        int allPoints = 0;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString(1) != null) {
                    allPoints = Integer.parseInt(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPoints;
    }

    // ====================================================================== \\

}


