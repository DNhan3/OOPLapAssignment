package model;

public class Admin extends User {
    public Admin(String userId, String userName, String userPassword,
            String userRegisterTime, String userRole) {
        super(userId, userName, userPassword, userRegisterTime, userRole != null ? userRole : "admin");
    }

    public Admin() {
        super("u_0000000001", "admin", "adminpass",
                java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss")),
                "admin");
    }

    @Override
    public String toString() {
        return "{\"user_id\":\""        + getUserId() + 
            "\", \"user_name\":\""      + getUserName() +
            "\", \"user_password\":\""  + getUserPassword() + 
            "\"user_register_time\":\"" + getUserRegisterTime() + 
            "\", \"user_role\":\""      + getUserRole() + 
            "\"}";
    }
}
