package io.github.some_example_name.Model;
import com.badlogic.gdx.Gdx;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
            instance.connect();
        }
        return instance;
    }


    public void connect() {
        try {
            String dbPath = Gdx.files.local("mydata.db").path();
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "password TEXT NOT NULL, " +
            "questionNumber INTEGER, " +
            "answer TEXT, " +
            "avatarNumber INTEGER, " +
            "score INTEGER DEFAULT 0, " +
            "kills INTEGER DEFAULT 0, " +
            "survive INTEGER DEFAULT 0)";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }

    public void insertUser(String name, String password, int questionNumber, String answer,  int avatarNumber, int score, int kills, int survive) {
        String sql = "INSERT INTO users (name, password, questionNumber, answer, avatarNumber, score, kills, survive) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setInt(3, questionNumber);
            pstmt.setString(4, answer);
            pstmt.setInt(5, avatarNumber);
            pstmt.setInt(6, score);
            pstmt.setInt(7, kills);
            pstmt.setInt(8, survive);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String name) {
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"),
                    rs.getString("password"),
                    rs.getInt("questionNumber"),
                    rs.getString("answer"),
                    rs.getInt("avatarNumber"));
                user.setScore(rs.getInt("score"));
                user.setKills(rs.getInt("kills"));
                user.setSurvive(rs.getInt("survive"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET " +
            "password = ?, " +
            "questionNumber = ?, " +
            "answer = ?, " +
            "avatarNumber = ?, " +
            "score = ?, " +
            "kills = ?, " +
            "survive = ? " +
            "WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getPassword());
            pstmt.setInt(2, user.getQuestionNumber());
            pstmt.setString(3, user.getAnswer());
            pstmt.setInt(4, user.getAvatarNumber());
            pstmt.setInt(5, user.getScore());
            pstmt.setInt(6, user.getKills());
            pstmt.setInt(7, user.getSurvive());
            pstmt.setString(8, user.getName());  // 'name' is used as the identifier
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(rs.getString("name"),
                    rs.getString("password"),
                    rs.getInt("questionNumber"),
                    rs.getString("answer"),
                    rs.getInt("avatarNumber"));
                user.setScore(rs.getInt("score"));
                user.setKills(rs.getInt("kills"));
                user.setSurvive(rs.getInt("survive"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void deleteUser(String name) {
        String sql = "DELETE FROM users WHERE name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
