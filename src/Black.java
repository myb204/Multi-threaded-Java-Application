import java.util.ArrayList;
import java.util.Random;

/**
 * This class is a subclass of the Bag superclass. It creates a Black Bag
 * which is a bag with another bag linked as an attribute.
 */

public class Black extends Bag {

    /**
     * This constructor takes a bag, bag name and the linked White Bag object.
     *
     * @param Bag This is an ArrayList of integers.
     * @param bagName This String value is the name of the bag object.
     * @param linkedBag This White Bag object is the linked White Bag.
     */

    public Black(ArrayList<Integer> Bag, String bagName, White linkedBag){
        super(Bag, bagName);
        this.linkedBag = linkedBag;
    }

    /**
     * This method gets the linked White Bag of a Black Bag object.
     * @return linkedBag This is the White Bag that is linked to the Black Bag.
     */

    public White getLinkedBag() {
        return this.linkedBag;
    }

    /**
     * This method randomly selects a pebble from the bag and removes it.
     *
     * @return pebble This int value is the pebble drawn from a bag.
     */

    public int drawPebble(){
        Random random = new Random();
        int index = random.nextInt(bag.size());
        int pebble = this.bag.get(index);
        this.bag.remove(index);
        return pebble;
    }

    /**
     * This method takes a bag and it's linked White Bag. It gets the linked White Bag
     * and adds all of it's pebbles into the Black Bag it is linked to, then it clears
     * the White Bag.
     *
     * @param Bag This is an ArrayList of integers and represents the Black Bag.
     * @param linkedBag This is a White Bag that is linked to the Black Bag.
     */

    public void refillBag(ArrayList<Integer> Bag, White linkedBag){
        this.bag.addAll(this.linkedBag.getBag());
        this.linkedBag.clearBag();
    }


}
