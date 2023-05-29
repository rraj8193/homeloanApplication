package AppliactionResources;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	static Connection conn;
	public static Connection createConnection() {
		try {
	    	String driver = "com.mysql.cj.jdbc.Driver";
	    	String url = "jdbc:mysql://localhost:3306/homeloanapp";
	    	String username = "root";
	    	String password = "708751";
	    	
	    	Class.forName(driver);
	    	
	    	conn = DriverManager.getConnection(url,username,password);
	    	
//	    	System.out.println("Database connected");
	    }
	     catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		return conn;	
	}
}
