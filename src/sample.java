
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class sample {
	
	
public static void main(String[] args) throws SQLException {
// Connect to database
final String hostName = "tayl6382-sql-server.database.windows.net";
final String dbName = "cs-dsa-4513-sql-db";
final String user = "tayl6382";
final String password = "Group33password";
final String url =
String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
hostName, dbName, user, password);
try (final Connection connection = DriverManager.getConnection(url)) {
final String schema = connection.getSchema();
System.out.println("Successful connection - Schema:" + schema);
System.out.println("Query data example:");
System.out.println("=========================================");
final String selectSql = "SELECT * FROM office_hours;";
try (final Statement statement = connection.createStatement();
final ResultSet resultSet = statement.executeQuery(selectSql)) {
System.out.println("Contents of the office_hours table:");
while (resultSet.next()) {
System.out.println(String.format("%s | %s | %s | %s | %s | %s | %s",
resultSet.getString(1),
resultSet.getString(2),
resultSet.getString(3),
resultSet.getString(4),
resultSet.getString(5),
resultSet.getString(6),
resultSet.getString(7)));
}
}
}
}
}
