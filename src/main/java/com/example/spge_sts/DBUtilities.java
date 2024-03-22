package com.example.spge_sts;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtilities {

    private DBUtilities() {
    }

    private static final String dbUrl = "jdbc:mysql://localhost:3306/spge_sts_2";
    private static final String dbUser = "root";
    private static final String dbPassword = "bocanito";

    // ====================================================================== \\

    /* CREATE queries */

    protected static boolean createUser(String username, String password, String first_name, String last_name, String email, String phone, String user_role) {
        String query = "INSERT INTO users (username, password, first_name, last_name, email, phone, user_role) VALUES (?, ?, ?, ?, ?, ?, ?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, first_name);
            preparedStatement.setString(4, last_name);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, user_role);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static boolean createTest(String creator_id, String test_name, String description, String code, String response_time, String passing_score, String questions_count, String date_created, String date_updated, String status) {
        String query = "INSERT INTO tests (creator_id, test_name, description, code, response_time, passing_score, questions_count, date_created, date_updated, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, creator_id);
            preparedStatement.setString(2, test_name);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, code);
            preparedStatement.setString(5, response_time);
            preparedStatement.setString(6, passing_score);
            preparedStatement.setString(7, questions_count);
            preparedStatement.setString(8, date_created);
            preparedStatement.setString(9, date_updated);
            preparedStatement.setString(10, status);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static boolean createQuestion(String test_id, String question_text, String question_type, String answers, String correct_answer, String points, String number) {
        String query = "INSERT INTO questions (test_id, question_text, question_type, answers, correct_answer, points, number) VALUES (?, ? ,? ,? ,? ,?, ?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, test_id);
            preparedStatement.setString(2, question_text);
            preparedStatement.setString(3, question_type);
            preparedStatement.setString(4, answers);
            preparedStatement.setString(5, correct_answer);
            preparedStatement.setString(6, points);
            preparedStatement.setString(7, number);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static boolean createResponse(String question_id, String user_id, String response_text, String selected_options, String is_correct, String submission_timestamp) {
        String query = "INSERT INTO responses (question_id, user_id, response_text, selected_options, is_correct, submission_timestamp) VALUES (?, ? ,? ,? ,? ,?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, question_id);
            preparedStatement.setString(2, user_id);
            preparedStatement.setString(3, response_text);
            preparedStatement.setString(4, selected_options);
            preparedStatement.setString(5, is_correct);
            preparedStatement.setString(6, submission_timestamp);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static boolean createResult(String user_id, String test_id, String score, String date_time_completion, String duration_minutes, String pass_fail_status) {
        String query = "INSERT INTO results (user_id, test_id, score, date_time_completion, duration_minutes, pass_fail_status) VALUES (?, ?, ?, ?, ?, ?);";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, test_id);
            preparedStatement.setString(3, score);
            preparedStatement.setString(4, date_time_completion);
            preparedStatement.setString(5, duration_minutes);
            preparedStatement.setString(6, pass_fail_status);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // ====================================================================== \\

    /* READ queries*/

    protected static Map<String, String> getUserData(String user_id) {
        Map<String, String> data = new HashMap<>();
        String sqlQuery = "SELECT * from users WHERE user_id = " + user_id + ";";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                data.put("UserID", resultSet.getString(1));
                data.put("Username", resultSet.getString(2));
                data.put("Password", resultSet.getString(3));
                data.put("FirstName", resultSet.getString(4));
                data.put("LastName", resultSet.getString(5));
                data.put("Email", resultSet.getString(6));
                data.put("Phone", resultSet.getString(7));
                data.put("UserRole", resultSet.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    protected static Map<String, String> getQuestionData(String question_id) {
        Map<String, String> data = new HashMap<>();
        String sqlQuery = "SELECT * FROM questions WHERE question_id = " + question_id + ";";
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
        String query = "SELECT user_id, username, user_role FROM users";
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
            query = "SELECT user_id FROM users WHERE user_id LIKE '%" + string + "%';";
        } else if (criterion.equals("Username")) {
            query = "SELECT user_id FROM users WHERE username LIKE '%" + string + "%';";
        } else {
            query = "SELECT user_id FROM users WHERE user_role LIKE '%" + string + "%';";
        }
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                IDs.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IDs;
    }

    protected static String getLastInsertedTestID() {
        String lastTestID = null;
        String lastTestQuestions = null;
        String query = "SELECT test_id, questions_count FROM tests ORDER BY test_id DESC LIMIT 1;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lastTestID = resultSet.getString(1);
                lastTestQuestions = resultSet.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastTestID + " " + lastTestQuestions;
    }

    protected static ArrayList<String> getQuestionIDs(String code) {
        ArrayList<String> questionIDs = new ArrayList<>();
        String sqlQuery = "SELECT question_id FROM questions INNER JOIN tests ON questions.test_id = tests.test_id WHERE code = '" + code + "';";
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

    protected static int getPassingScoreOfTestBy(String test_id) {
        String query = "SELECT passing_score FROM tests WHERE test_id = " + test_id + ";";
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
        String query = "SELECT response_time FROM tests WHERE code = '" + code + "';";
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

    protected static String getTestStatus(String test_id) {
        String query = "SELECT status FROM tests WHERE test_id = " + test_id + ";";
        String status = "";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                status = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    protected static int getCountOfTestsCreateByUser(String creator_id) {
        String query = "SELECT COUNT(test_id) FROM tests WHERE creator_id = " + creator_id + ";";
        int count = -1;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = Integer.parseInt(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    protected static ArrayList<String> getTestIDsCreateByUser(String creator_id) {
        String query = "SELECT test_id FROM tests WHERE creator_id = " + creator_id + ";";
        ArrayList<String> testIDs = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                testIDs.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testIDs;
    }

    protected static ArrayList<String> getTestNamesCreateByUser(String creator_id) {
        String query = "SELECT test_name FROM tests WHERE creator_id = " + creator_id + ";";
        ArrayList<String> testNames = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                testNames.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testNames;
    }

    protected static int getCountOfResultsFromTest(String test_id) {
        String query = "SELECT COUNT(result_id) FROM results WHERE test_id = " + test_id + ";";
        int count = -1;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = Integer.parseInt(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    protected static int getSumOfScores(String test_id) {
        String query = "SELECT SUM(score) FROM results WHERE test_id = ?;";
        int sum = 0;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, test_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sum = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    protected static int getCountOfStatusResultsFromTest(String status, String test_id) {
        String query = "SELECT COUNT(pass_fail_status) FROM results WHERE pass_fail_status = '" + status + "' AND test_id = " + test_id + ";";
        int count = -1;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = Integer.parseInt(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    protected static String getRecordsCount(String table) {
        String query = "SELECT COUNT(*) FROM " + table + ";";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static String getUsersCountByRole(String user_role) {
        String query = "SELECT COUNT(user_id) FROM users WHERE user_role = ?;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user_role);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static String getResultsCountByStatus(String pass_fail_status) {
        String query = "SELECT COUNT(*) FROM results WHERE pass_fail_status = ?;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pass_fail_status);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static String getResponsesCountByStatus(String is_correct) {
        String query = "SELECT COUNT(*) FROM responses WHERE is_correct = ?;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, is_correct);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static String getTestsCountByStatus(String status) {
        String query = "SELECT COUNT(*) FROM tests WHERE status = ?;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static String getQuestionsCountByType(String question_type) {
        String query = "SELECT COUNT(*) FROM questions WHERE question_type = ?;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, question_type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    protected static boolean updateTestStatus(String status, String test_id) {
        String query = "UPDATE tests SET status = '" + status + "' WHERE test_id = " + test_id + ";";
        boolean successfulChange = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            successfulChange = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return successfulChange;
    }

    protected static void updateTestDate(String date_updated, String test_id) {
        String query = "UPDATE tests SET date_updated = '" + date_updated + "' WHERE test_id = " + test_id + ";";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            boolean b = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static String getCodeOfTest(String test_id) {
        String query = "SELECT code FROM tests WHERE test_id = " + test_id + ";";
        String code = null;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                code = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;
    }

    protected static ArrayList<String> getTestLeaderboard(String test_id) {
        String query = "SELECT username, score, duration_minutes FROM results AS r JOIN users AS u ON r.user_id = u.user_id WHERE test_id = " + test_id + " ORDER BY score DESC, username;";
        ArrayList<String> leaderboard = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 1;
            while (resultSet.next()) {
                String result = i + ". " + resultSet.getString(1) + " - " + resultSet.getString(2) + " p. (" + resultSet.getString(3) + ")";
                leaderboard.add(result);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    protected static String getTestDateCreation(String test_id) {
        String query = "SELECT date_created FROM tests WHERE test_id =" + test_id + ";";
        String date_created = null;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                date_created = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return date_created;
    }

    protected static Map<String, Integer> getBarChartData(String test_id) {
        String query = "SELECT score, COUNT(score) FROM results WHERE test_id = " + test_id + " GROUP BY score ORDER BY score;";
        Map<String, Integer> pointsCountMap = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pointsCountMap.put(resultSet.getString(1), Integer.parseInt(resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pointsCountMap;
    }

    protected static List<Map<String, String>> getTestQuestions(String test_id) {
        String query = "SELECT * FROM questions WHERE test_id = " + test_id + ";";
        List<Map<String, String>> questionsData = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Map<String, String> question = new HashMap<>();
                question.put("question_id", resultSet.getString(1));
                question.put("question_text", resultSet.getString(3));
                question.put("answers", resultSet.getString(5));
                question.put("correct_answer", resultSet.getString(6));
                question.put("points", resultSet.getString(7));
                questionsData.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionsData;
    }


    // ====================================================================== \\

    /* DELETE queries*/

    protected static boolean deleteUser(String user_id) {
        String query = "DELETE FROM users WHERE user_id = " + user_id + ";";
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected static boolean deleteTest(String test_id) {
        String query = "DELETE FROM tests WHERE test_id = " + test_id + ";";
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

    private static String makeUpdateUserDataQuery(String string, String user_id) {
        String sqlQuery = null;
        if (string.matches(ValidationRegexes.TEACHER_USERNAME.getRegex()) || string.matches(ValidationRegexes.STUDENT_USERNAME.getRegex())) {
            String firstName = String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1, string.indexOf('_'));
            String lastNameFirstCharacter = String.valueOf(string.charAt(string.indexOf('_') + 1));
            if (string.matches(ValidationRegexes.TEACHER_USERNAME.getRegex())) {
                String lastName = lastNameFirstCharacter.toUpperCase() + string.substring(string.indexOf('_') + 2, string.indexOf('@'));
                sqlQuery = "UPDATE users SET username = '" + string + "', first_name = '" + firstName + "', last_name = '" + lastName + "' WHERE user_id = '" + user_id + "';";
            } else if (string.matches(ValidationRegexes.STUDENT_USERNAME.getRegex())) {
                String lastName = lastNameFirstCharacter.toUpperCase() + string.substring(string.indexOf('_') + 2, string.lastIndexOf('_'));
                sqlQuery = "UPDATE users SET username = '" + string + "', first_name = '" + firstName + "', last_name = '" + lastName + "' WHERE user_id = '" + user_id + "';";
            }
        } else if (string.matches(ValidationRegexes.LAST_NAME.getRegex())) {
            String username = getUserData(user_id).get("Username");
            String updatedUsername;
            if (username.matches(ValidationRegexes.TEACHER_USERNAME.getRegex())) {
                updatedUsername = username.substring(0, username.indexOf('_') + 1) + string.toLowerCase() + username.substring(username.indexOf('@'));
            } else {
                updatedUsername = username.substring(0, username.indexOf('_') + 1) + string.toLowerCase() + username.substring(username.lastIndexOf('_'));
            }
            sqlQuery = "UPDATE users SET last_name = '" + string + "', username = '" + updatedUsername + "' WHERE user_id = '" + user_id + "';";
        } else if (string.matches(ValidationRegexes.FIRST_NAME.getRegex())) {
            String username = getUserData(user_id).get("Username");
            String updatedUsername = string.toLowerCase() + username.substring(username.indexOf('_'));
            sqlQuery = "UPDATE users SET first_name = '" + string + "', username = '" + updatedUsername + "' WHERE user_id = '" + user_id + "';";
        } else if (string.matches(ValidationRegexes.EMAIL.getRegex())) {
            sqlQuery = "UPDATE users SET email = '" + string + "' WHERE user_id = '" + user_id + "';";
        } else if (string.matches(ValidationRegexes.PHONE.getRegex())) {
            sqlQuery = "UPDATE users SET phone = '" + string + "' WHERE user_id = '" + user_id + "';";
        }
        return sqlQuery;
    }

    protected static String suchUserExists(String username, String password) {
        String sqlQuery = "SELECT user_id FROM users WHERE username = '" + username + "' AND password = '" + password + "';";
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
        String query = "SELECT status FROM tests WHERE code = '" + code + "';";
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

    protected static int calculatePointsReceivedByStudent(String user_id, String test_id) {
        String query = "SELECT SUM(points) AS TotalPoints FROM responses r INNER JOIN questions q ON r.question_id = q.question_id INNER JOIN tests t ON q.test_id = t.test_id WHERE r.user_id = " + user_id + " AND r.is_correct = 'true' AND t.test_id = " + test_id + ";";
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

    protected static int calculateAllPointsOfATest(String test_id) {
        String query = "SELECT SUM(points) AS MaxPoints FROM questions WHERE test_id = " + test_id + ";";
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

    protected static boolean studentHasAlreadyEnteredTheTest(String user_id, String code) {
        String query = "SELECT COUNT(*) AS ResponseCount FROM responses r JOIN questions q ON r.question_id = q.question_id JOIN tests t ON q.test_id = t.test_id WHERE r.user_id = " + user_id + " AND t.code = '" + code + "';";
        boolean flag = true;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (Integer.parseInt(resultSet.getString(1)) == 0) {
                    flag = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    protected static int calculateDurationInSeconds(String test_id, String user_id) {
        String query = "SELECT TIMESTAMPDIFF(SECOND, MIN(r.submission_timestamp), MAX(r.submission_timestamp)) AS TimeSpentInSeconds FROM responses r JOIN questions q ON r.question_id = q.question_id WHERE q.test_id = " + test_id + " AND r.user_id = " + user_id + ";";
        int minutes = -1;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                minutes = Integer.parseInt(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return minutes;
    }

    protected static String sumPointsOfAllQuestions() {
        String query = "SELECT SUM(points) FROM questions;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static String sumPassingScoreOfAllTests() {
        String query = "SELECT SUM(passing_score) FROM tests;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static String sumQuestionsOfAllTests() {
        String query = "SELECT SUM(questions_count) FROM tests;";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ====================================================================== \\

}


