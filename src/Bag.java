import java.util.ArrayList;

/**
 * This is a superclass, Bag. A bag object has a name, and is an ArrayList of integers.
 * A bag can also be a White Bag, or a Black Bag.
 */

public class Bag {

    protected ArrayList<Integer> bag;
    protected White linkedBag;
    protected String bagName;

    /**
     * This constructor creates a bag which has an ArrayList of integers
     * and a name.
     *
     * @param bag This value is an ArrayList of integers.
     * @param bagName This String value is the name of the bag.
     */

    public Bag(ArrayList<Integer> bag, String bagName){
        this.bag = bag;
        this.bagName = bagName;
    }

    /**
     * This method adds a pebble into a bag.
     *
     * @param pebble This int value is a pebble which is added to a bag object.
     */

    public void addPebble(int pebble){
        this.bag.add(pebble);
    }

    /**
     * This method gets the name of the bag.
     *
     * @return The String value of the bag object's name.
     */

    public String getBagName() {
        return this.bagName;
    }

    /**
     * This method gets the size of the bag.
     *
     * @return The int value of the size of the bag object.
     */

    public int getSize(){
        return this.bag.size();
    }

    /**
     * This method gets the current bag.
     *
     * @return The bag object is returned.
     */

    public ArrayList<Integer> getBag(){
        return this.bag;
    }

}
