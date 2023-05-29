package LoanPackage;

public interface LoanDAO {
	public boolean isEligibleForLoan();
	public void setInDb(LoanModel l,int user_id);

}
