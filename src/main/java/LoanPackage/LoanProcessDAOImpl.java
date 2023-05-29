package LoanPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import AppliactionResources.DBConnection;
import EMIPackage.Emi;
import UserPackage.UserModel;

import java.io.*;

public class LoanProcessDAOImpl {
	
	
	public void getInformation(UserModel user,LoanModel l, String file) {
		Connection conn = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("\n******Your LOAN is in process.");
		
//		System.out.println("\nEnter 'Y' to provide documents.");
//		char ch = sc.next().charAt(0);
//		if(ch != 'Y')
//		return;
		System.out.println();
		
		int loanid=0;
		
		try {
			String selectLoanid="select loan_id from loan where user_id="+user.getUser_id();
			ResultSet rs1= null;
			Connection con= DBConnection.createConnection();
			Statement ps1= con.createStatement();
//			ps1.setInt(loanid, user.getUser_id());
			rs1= ps1.executeQuery(selectLoanid);
//			loanid= rs1.getInt("loan_id");  loan_id ; 
			while(rs1.next()) {
				loanid = rs1.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				
		String q = " INSERT INTO  loan_process(loan_id,document,annual_income,cibil_score) VALUES(?,?,?,?);";
		
		try {
			conn = DBConnection.createConnection();

			
			PreparedStatement ps = conn.prepareStatement(q);
			
			File f = new File(file);
			FileInputStream fis = new FileInputStream(f);

//		System.out.println("abcd");
		
			ps.setInt(1, loanid);
			
			ps.setString(2, file);
			ps.setInt(3, l.getAnnual_income());
			ps.setInt(4, 700);
			
			int count = ps.executeUpdate();
//			System.out.println("qwert");
			if(count != 0) {
				System.out.println("Loan_process Successfully UPDATED!!!!\n\n");
				new Emi(user.getUser_id(),loanid);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
