package LoanPackage;

import java.sql.Date;
import java.util.*;

import UserPackage.UserModel;

public class LoanModel {
	private int loan_id;
	private int user_id;
	private Date application_date;
	private Date sanction_date;
	private int duration;
	private float interest_rate;
	private String status;

	@Override
	public String toString() {
		return "Loan [loan_id=" + loan_id + ", user_id=" + user_id + ", application_date=" + application_date
				+ ", sanction_date=" + sanction_date + ", duration=" + duration + ", interest_rate=" + interest_rate
				+ ", status=" + status + ", loan_amount=" + loan_amount + ", annual_income=" + annual_income + "]";
	}

	private int loan_amount;
	private int annual_income;

	public static void getLoanAttributesFromUser(LoanModel l) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the loan duration (in years): ");
        int duration = scanner.nextInt();
        l.setDuration(duration);
        

        System.out.print("Enter the loan amount: ");
        int loanAmount = scanner.nextInt();
        l.setLoan_amount(loanAmount);

        System.out.print("Enter the annual income: ");
        int annualIncome = scanner.nextInt();
        l.setAnnual_income(annualIncome);
    }
	
	public LoanModel() {}

	public LoanModel(UserModel user) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Apply For Loan");
		int choice = sc.nextInt();
		switch(choice) {
			case 1:
				LoanModel l = new LoanModel();
				l.setUser_id(user.getUser_id());
				l.application_date = new Date(System.currentTimeMillis());
				l.setInterest_rate(12.00f);
				l.sanction_date = null;
				l.status = "pending";
				
				getLoanAttributesFromUser(l);
				
				LoanDAOImpl ldi = new LoanDAOImpl();
				ldi.setInDb(l, user.getUser_id());
				System.out.println("\nEnter 'Y' to initiate loan process.");
				char ch = sc.next().charAt(0);
				if (ch != 'Y') {
					System.out.println("Thank you!!!");
					return;
				}
				LoanProcessDAOImpl lpdi = new LoanProcessDAOImpl();
				String file = "C:\\Users\\Rishav Raj\\Desktop\\data.pdf";
				lpdi.getInformation(user, l, file);
				
		}
		
	}

	public int getLoan_id() {
		return this.loan_id;
	}

	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getApplication_date() {
		return application_date;
	}

	public void setApplication_date(Date application_date) {
		this.application_date = application_date;
	}

	public Date getSanction_date() {
		return sanction_date;
	}

	public void setSanction_date(Date sanction_date) {
		this.sanction_date = sanction_date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public float getInterest_rate() {
		return interest_rate;
	}

	public void setInterest_rate(float interest_rate) {
		this.interest_rate = interest_rate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(int loan_amount) {
		this.loan_amount = loan_amount;
	}

	public int getAnnual_income() {
		return annual_income;
	}

	public void setAnnual_income(int annual_income) {
		this.annual_income = annual_income;
	}
}
