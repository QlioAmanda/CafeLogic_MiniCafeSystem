package cafe.service;

public class LoginService {
    private static LoginService instance;

    private LoginService() {}

    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    public boolean loginAdmin(String user, String pass) {
        return user.equals("admin") && pass.equals("123");
    }
}
