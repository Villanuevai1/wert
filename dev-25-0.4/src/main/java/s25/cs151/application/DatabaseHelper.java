package s25.cs151.application;

import java.sql.*;

public class DatabaseHelper {

    private static final String DB_PATH = System.getProperty("user.home") + "/identifier.sqlite";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS office_hours ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "semester TEXT NOT NULL, "
                + "year INTEGER NOT NULL, "
                + "day TEXT NOT NULL, "
                + "time TEXT NOT NULL,"
                +"UNIQUE (semester, year)"
                + ");";

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
                System.out.println(" Database initialized: " + DB_PATH);
            }
        } catch (ClassNotFoundException e) {
            System.err.println(" SQLite JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(" Error initializing DB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertOfficeHour(String semester, int year, String day, String time) {
        String query = "INSERT INTO office_hours (semester, year, day, time) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            System.out.println(" INSERTING INTO DB FILE: " + DB_PATH);
            stmt.setString(1, semester);
            stmt.setInt(2, year);
            stmt.setString(3, day);
            stmt.setString(4, time);
            stmt.executeUpdate();

            System.out.println(" Insert successful!");

        } catch (SQLException e) {
            System.err.println(" INSERT FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void debugPrintAll() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM office_hours")) {

            System.out.println(" Current office_hours table:");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("semester") + " | " +
                                rs.getInt("year") + " | " +
                                rs.getString("day") + " | " +
                                rs.getString("time")
                );
            }

        } catch (SQLException e) {
            System.err.println(" Error reading table: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public boolean officeHourExists(String semester, int year) {
        String query = "SELECT COUNT(*) FROM office_hours WHERE semester = ? AND year = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, semester);
            stmt.setInt(2, year);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("🔍 Checking DB for: " + semester + " " + year + " → Found: " + count);
                    return count > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println(" Failed to check for duplicates: " + e.getMessage());
        }

        return false;
    }


}
