package LoanPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;

import AppliactionResources.DBConnection;

public class LoanDAOImpl implements LoanDAO{

	public boolean isEligibleForLoan() {
		// TODO Auto-generated method stub
		System.out.println();
		return false;
	}

	public void setInDb(LoanModel l,int user_id) {
		
		Connection con =  DBConnection.createConnection();
		String q = "INSERT INTO  loan(user_id,application_date, sanction_date,duration,"
				+ "interest_rate,status,loan_number) "
				+ "VALUES(?,?,?,?,?,?,?);";	
		
		try {
			PreparedStatement ps = con.prepareStatement(q);
			ps.setInt(1, user_id);
			ps.setDate(2, l.getApplication_date());
			ps.setDate(3, l.getSanction_date());
			ps.setInt(4, l.getDuration());
			ps.setFloat(5, l.getInterest_rate());
			ps.setString(6, l.getStatus());
			ps.setInt(7, l.getLoan_amount());
			
			
			
			int count = ps.executeUpdate();
			if(count != 0) {
				System.out.println("Loan details UPDATED !!!!!!");
			}
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

}