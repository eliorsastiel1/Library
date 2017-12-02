package elior.library;

/**
 * Created by Elior on 12/2/2017.
 */

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String Password;

    public User(String firstName, String lastName, String email, String address, String Password ){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.address=address;
        this.Password=Password;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setAddresse(String address){
        this.address=address;
    }
    public void setPassword(String Password){
        this.Password=Password;
    }

}
