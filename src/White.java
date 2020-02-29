import java.util.ArrayList;

/**
 * This class is a subclass of the class Bag. This class creates a White Bag object.
 */

public class White extends Bag{

    /**
     * This constructor takes a bag and a bag name
     * and creates a White bag object.
     *
     * @param bag This bag object is an ArrayList of integers.
     * @param bagName This String value is the name of the White Bag.
     */

    public White(ArrayList<Integer> bag, String bagName){
        super(bag, bagName);
    }

    /**
     * This method clears the current bag. This is used after the White Bag is emptied
     * into the Black Bag.
     */

    public void clearBag(){
        this.bag.clear();
    }
}
