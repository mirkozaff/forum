package utility_package;


public class User {
    
    public static String name;
    public static String password;
    public static String imageURL;

    public User() {
    }

    public static String getImageURL() {
        return imageURL;
    }

    public static String getName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }
}
