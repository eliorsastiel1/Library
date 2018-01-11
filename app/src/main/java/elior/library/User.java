package elior.library;

/**
 * Created by Elior on 12/2/2017.
 */

public class User {
    public String firstName;
    public String lastName;
    public String email;
    public String address;
    public String Password;
    public String ID;

    public User(){

    }

    public String getEmailAddress(){
        return email;
    }

    public static boolean isValidEmail(User u){
        if(u.email!=null){
            return true;
        }
        else return false;
    }

}
