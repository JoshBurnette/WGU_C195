package model;

/**
 This class is used to construct Countries objects.
 It has getters.
 */
public class Countries {
    private int id;
    private String name;

    /**
     This method is used to construct a Countries object.
     */
    //constructor
    public Countries(int id, String name){
        this.id = id;
        this.name = name;
    }

    //getters
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
