package model;

/**
 This class is used to construct Contact objects.
 It has getters.
 */
public class Contact {

    private int id;
    private String name;
    private String email;

    /**
     This method is used to construct a Contact object.
     */
    //Contact constructor
    public Contact(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    //getters ... not sure that I'll need to get contact name or email but, maybe
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail() {
        return email;
    }
}
