package EMIPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import AppliactionResources.DBConnection;

class EMI__{
	int emi_id;
	int user_id;
	int loan_id;
	double emi_amount;
	double total_amount_left;
	int duration;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.emi_id+"-"+this.user_id+"-"+this.loan_id+"-"+this.emi_amount+"-"+this.total_amount_left+"-"+this.duration ;
	}
}

public class Emi {

	EMI__ emid_data;
	public  Emi(int id,int loanId) throws SQLException{
		
		if(checkLoanPresent(loanId)) {
			this.emid_data = getData(loanId);
			Emipanel(emid_data);
		}
		else {
			System.out.println("No loan is present");
		}
		
	}
	
	public static void Emipanel(EMI__ e) throws SQLException {
		
		Scanner sc = new Scanner(System.in);
			System.out.println("Enter amount to pay emi.");
			double d = sc.nextDouble();
			
				if(d < e.emi_amount ) {
					System.out.println("Emi amoust must be greater that "+e.emi_amount);
				}
				else if(d>e.total_amount_left) {
					System.out.println("The emi amount must be less that total amount left.");
				}
				else {
					payEmi(e, d);
				}
	}
	
	public static void payEmi(EMI__ e,double amount) throws SQLException {
	
		DBConnection db = new DBConnection();
    	Connection conn = db.createConnection();
    	
    	Statement stmt = conn.createStatement();
    	LocalDate dt = LocalDate.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dt.format(formatter);
        
        Statement getCount = conn.createStatement();
        ResultSet rs = getCount.executeQuery("Select count(*) as total_count from emi_audit");
        int count=0;
        while(rs.next()) {
        	 count = rs.getInt("total_count");
        }
        
        System.out.println(count);
    	String query = "Insert into emi_audit (transaction_id, emi_id, amount_paid, date_of_payment) values (?,?,?,?);";
    	PreparedStatement preparedStatement = conn.prepareStatement(query);
    	preparedStatement.setInt(1, count+1);
    	preparedStatement.setInt(2, e.emi_id);
    	preparedStatement.setDouble(3,amount);
    	preparedStatement.setString(4, formattedDate);
    	//System.out.println(preparedStatement);
    	//preparedStatement.executeQuery();
    	int rowsAffected = preparedStatement.executeUpdate();
        
        // Checking the number of affected rows
        //System.out.println("Rows affected: " + rowsAffected);
       
    	System.out.println("Emi got paid.");
    	System.out.println((getData(e.user_id).total_amount_left) +" left for this month to be paid");
	}
	
	public static EMI__ getData(int loanId) {
		EMI__ e1 = new EMI__();
		try {
	    	
	    	Connection conn = DBConnection.createConnection();
	    	
	    	Statement stmt = conn.createStatement();
	    	
	    	//--changes required 
	    	String sql = "Select emi_id,loan_id,emi_amount,total_amount_left,emi_date from emi where loan_id = "+loanId;
	    	
	    	ResultSet rs = stmt.executeQuery(sql);
	    	
	    	
	    	while(rs.next()) {
	    	    
	    		e1.emi_id = rs.getInt("emi_id");
//	    		e1.user_id = rs.getInt("user_id");
	    		e1.loan_id = rs.getInt("loan_id");
	    		e1.emi_amount = rs.getDouble("emi_amount");
	    		
	    		e1.total_amount_left = rs.getDouble("total_amount_left");
//	    		e1.duration = rs.getInt("emi_date");
	    		
	    		
	    	}
	    	
	    	
	    	try {
	    		rs.close();
	    		stmt.close();
	    	}
	    	catch(SQLException sqe) {
	    		sqe.printStackTrace();
	    	}
	    	return e1;
	    	
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	
	    }
		
		return e1;
	}
	
	public static boolean checkLoanPresent(int loanid) {
		
		try {
	    	DBConnection db = new DBConnection();
	    	Connection conn = db.createConnection();
	    	
	    	Statement stmt = conn.createStatement();
	    	String sqli = "Select loan_id from emi where emi.loan_id = "+loanid;
	    	//stmt.execute("use wileyjdbc");
	    	ResultSet rs = stmt.executeQuery(sqli);
	    	int res = -1;
	    	while(rs.next()) {
	    		res = rs.getInt("loan_id");
	    		
	    	}
	    	
	    	try {
	    		rs.close();
	    		stmt.close();
	    	}
	    	catch(SQLException sqe) {
	    		sqe.printStackTrace();
	    	}
	    	
	    	if(res == -1) return false;
	    	else return true;
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	
	    }
		return false;

}}
