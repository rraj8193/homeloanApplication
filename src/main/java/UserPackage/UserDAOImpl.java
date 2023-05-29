package UserPackage;
import java.sql.*;
import java.util.Scanner;

import AppliactionResources.DBConnection;
import LoanPackage.LoanModel;

public class UserDAOImpl {
	Connection con= null;
	
	public void addUser(UserModel user) {
		con =  DBConnection.createConnection();
		try {
			String query = "{CALL insert_user(?,?,?,?,?,?,?,?,?)}";

			CallableStatement stmt = con.prepareCall(query);
			stmt.setInt(1, user.getUser_id());

			stmt.setString(2, user.getAcc_number());

			stmt.setString(3, user.getName());

			stmt.setString(4, user.getMobile());

			stmt.setString(5, user.getAddress());

			stmt.setString(6, user.getGender().toLowerCase());

			stmt.setString(7, user.getPancard().toUpperCase());

			stmt.setString(8, user.getDob());

			stmt.setInt(9, user.getAcc_type());

			int count = stmt.executeUpdate();
		
			if(count>0) {
				System.out.println("Data Saved Successfully");
				
				new LoanModel(user);
			}
			else {
				System.out.println("Failed to Save Data");
			}
			
			
		} catch (Exception e) {
			System.out.println("Failed to Save Data");
			
			System.err.println("Inside UserDAOImpl");
			e.printStackTrace();
		}
	}
	
	public void loginUser(String acc_number, String name) {
		con =  DBConnection.createConnection();
		String q = "SELECT * FROM user where acc_number LIKE "+acc_number;

		try {
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q);


			if(rs.next())
			{
				System.out.println("Login SuccessFull");
			}
			
			else
				System.out.println("Invalid input");
			
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}		

	}

	public UserDAOImpl(){
		Scanner obj = new Scanner(System.in);
		System.out.println("1.	Register\n2.	Login\n");
		int choice = obj.nextInt();
		switch(choice){
		case 1:
		{
			System.out.println("Enter User Id");
			int user_id = obj.nextInt();
			obj.nextLine();
			System.out.println("Enter Full name");
			String name = obj.nextLine();
			System.out.println("Enter account number");
			String acc_number = obj.next();
			obj.nextLine();
			System.out.println("Enter mobile number");
			String mobile = obj.nextLine();

			System.out.println("Enter address");
			String address = obj.nextLine();

			System.out.println("Enter gender");
			String gender = obj.nextLine();
			System.out.println("Enter pan number");
			String pan = obj.nextLine();
			System.out.println("Enter Date of birth(Eg:2003-01-27) : ");
			String dob = obj.nextLine();
			System.out.println("Enter account type(1. Current\t2. Saving\t3.  Loan ) : ");
			int acc_type = obj.nextInt();
			obj.nextLine();

			UserModel user = new UserModel(user_id,acc_number, name,mobile,address,gender,pan,dob,acc_type);
			addUser(user);
			break;
			
		}
		case 2:{
			System.out.println("Enter account number");
			String acc = obj.next();
			obj.nextLine();
			System.out.println("Enter name");
			String n = obj.nextLine();
			loginUser(acc,n);
			
			break;
		}
		default :
			System.out.println("Invalid input");
			
		}


	}

}




