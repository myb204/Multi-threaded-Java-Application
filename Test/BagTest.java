import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;


public class BagTest {

    private Bag testBag;

    @Before
    public void setUp() throws Exception {
        testBag = new Bag(new ArrayList<Integer>(), "testBag");
    }

    @After
    public void tearDown() throws Exception {
        testBag = null;
        assertNull(testBag);
    }

    @Test
    public void testBagCreation(){
        assertEquals("Wrong bag name created.", testBag.getBagName(), "testBag");
        assertEquals("Wrong bag selected.", testBag.getBag(), new ArrayList<Integer>());
    }

    @Test
    public void testAddToEmptyBag(){
        testBag.addPebble(10);
        assertEquals("Pebble was not added to bag.", testBag.getBag(), new ArrayList<Integer>(Arrays.asList(10)));
    }

    @Test
    public void testBagName(){
        assertEquals("Could not get bag name.", testBag.getBagName(), "testBag");
    }

    @Test
    public void testBagSize(){
        testBag.addPebble(10);
        testBag.addPebble(11);
        assertEquals("Bag size is not correct.", testBag.getSize(), 2);
    }


}