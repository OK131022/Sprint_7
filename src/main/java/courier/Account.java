package courier;

public class Account {
    private String login;
    private String password;

    public Account() {
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static Account from(Courier courier) {
        Account c = new Account();
        c.setLogin(courier.getLogin());
        c.setPassword(courier.getPassword());
        return c;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
