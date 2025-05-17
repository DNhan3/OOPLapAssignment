package model;

public class Customer extends User {
    private String userEmail;
    private String userMobile;

    public Customer(String userId, String userName, String userPassword,
                    String userRegisterTime, String userRole,
                    String userEmail, String userMobile) {
        super(userId, userName, userPassword, userRegisterTime, userRole);
        this.userEmail = userEmail;
        this.userMobile = userMobile;
    }

    public Customer() {
        super();
        this.userEmail = "default@example.com";
        this.userMobile = "0412345678";
    }

    @Override
    public String toString() {
        return "{\"user_id\":\""                + getUserId() +
               "\", \"user_name\":\""           + getUserName() +
               "\", \"user_password\":\""       + getUserPassword() +
               "\", \"user_register_time\":\""  + getUserRegisterTime() +
               "\", \"user_role\":\""           + getUserRole() +
               "\", \"user_email\":\""          + userEmail +
               "\", \"user_mobile\":\""         + userMobile +
                "\"}";
    }
}
