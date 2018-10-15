
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class sample {

	static Scanner input;

	public static void main(String[] args) throws SQLException {

		// Connect to database
		final String hostName = "tayl6382-sql-server.database.windows.net";
		final String dbName = "cs-dsa-4513-sql-db";
		final String user = "tayl6382";
		final String password = "Group33password";
		final String url = String.format(
				"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
				hostName, dbName, user, password);
		try (final Connection connection = DriverManager.getConnection(url)) {
			final String schema = connection.getSchema();
			System.out.println("Successful connection - Schema:" + schema);

			
			//start reading in option select
			input = new Scanner(System.in);

			while (true) {
				System.out.println("Enter 1, 2, 3, or 4:");
				int response = input.nextInt();
				switch (response) {
				case 1: //do option1 procedure
					option1(connection);
					break;
				case 2: //do option2 procedure
					option2(connection);
					break;
				case 3: // do option 3 procedure - print out tables
					option3(connection);
					break;
				case 4: //exit
					System.out.println("Exiting program.");
					return;
				default:
					System.out.println("Invalid Entry.");
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	//insert a new problem into Problem table
	public static void option1(Connection connection) {
		//call stored procedure option_1
		String query = "{ call option_1(?, ?, ?) }";

		CallableStatement call;
		try {
			call = connection.prepareCall(query);
		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}
		//get problem id from user input
		System.out.println("pid:");
		int pid = input.nextInt();
		
		//get problem name from user input
		System.out.println("pname:");
		String pname = input.next();

		// get author id from user input
		System.out.println("aid:");
		int aid = input.nextInt();

		//put in parameters and execute procedure
		try {
			call.setInt(1, pid);
			call.setString(2, pname);
			call.setInt(3, aid);

			call.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//give an author a raise
	public static void option2(Connection connection) {
		
		//execute stored proc option_2
		String query = "{ call option_2(?) }";

		CallableStatement call;
		try {
			call = connection.prepareCall(query);
		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}

		//get author id from user input
		System.out.println("aid:");
		int aid = input.nextInt();


		//put in parameters and execute procedure
		try {
			call.setInt(1, aid);

			call.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//print out contents of Author and Problem tables
	public static void option3(Connection connection) {
		final String selectSql = "SELECT * FROM Author;";
		Statement statement;
		try {
			statement = connection.createStatement();
			final ResultSet resultSet = statement.executeQuery(selectSql);
			System.out.println("Contents of the Author table:");
			while (resultSet.next()) {
				System.out.println(String.format("%s | %s | %s", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3)));
			}
		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}

		final String selectSql2 = "SELECT * FROM Problem;";
		Statement statement2;
		try {
			statement2 = connection.createStatement();
			final ResultSet resultSet = statement2.executeQuery(selectSql2);
			System.out.println("Contents of the Problem table:");
			while (resultSet.next()) {
				System.out.println(String.format("%s | %s | %s | %s", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4)));
			}
		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}
	}

}
