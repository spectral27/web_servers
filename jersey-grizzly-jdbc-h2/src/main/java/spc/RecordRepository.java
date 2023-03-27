package spc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordRepository {

    public static void init() throws SQLException {
        DriverManager.getConnection(
                "jdbc:h2:mem:testdatabase;INIT=runscript from 'classpath:create.sql'",
                "username",
                "password"
        );
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:h2:mem:testdatabase;DB_CLOSE_DELAY=-1",
                "username",
                "password"
        );
    }

    public List<RecordObject> selectRecords() {
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from records");
            List<RecordObject> records = new ArrayList<>();
            while (resultSet.next()) {
                RecordObject record = new RecordObject();
                record.setId(resultSet.getString("id"));
                record.setOrigin(resultSet.getString("origin"));
                records.add(record);
            }
            return records;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRecord(RecordObject record) {
        try (Connection conn = getConnection()) {
            String insertQuery = "insert into records values (?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, UUID.randomUUID().toString().replace("-", "").substring(0, 16));
            ps.setString(2, record.getOrigin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
