package UserPackage;

public interface UserDAO {
	public void addUser(UserModel user);
	public void loginUser(String account_number, String name);
}
