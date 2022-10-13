package unit_tests;

import static org.testng.Assert.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.main.selenium.selenium.DBDataReader;

public class TestDBReader {

	DBDataReader dbDataReader;
	
	@BeforeMethod
	public void setup() {
		dbDataReader = new DBDataReader();
		dbDataReader.connect();
	}
	
	@Test
	public void testConnection() throws SQLException {
		dbDataReader = new DBDataReader();
		dbDataReader.connect();
		assertNotNull(dbDataReader.getConnection());
	}
	
	@Test
	public void testDataRetrieveByColumns() throws SQLException {
		ResultSet rows =dbDataReader.getDataBy(Arrays.asList("id", "productName", "price"));
		assertNotNull(rows);
		while (rows.next()) {
			System.out.println(rows.getString("id") + ", " + rows.getString("productName") + ", " + rows.getFloat("price"));
		}
	}
}
