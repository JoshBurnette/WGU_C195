package model;

/**
 This class is used to construct User objects.
 It has getters and setters.
 */
public class User {

    private int userID;
    public String username;
    public String password;

    /**
     This method is used to construct a User object.
     */
    //constructor
    public User(int userID, String username, String password){
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    //setters and getters
    public String getUsername(){

        return username;
    }
    public void setUsername(){

    }
    public String getPassword(){
        return password;
    }
    public void setPassword(){
        //Don't think I need setters.
    }
    public int getUserID(){
        return userID;
    }


}
