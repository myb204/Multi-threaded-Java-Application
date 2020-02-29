import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;


public class BlackTest {

    private White testWhiteBag;
    private Black testBlackBag;

    @Before
    public void setUp() throws Exception {
        testWhiteBag = new White(new ArrayList<Integer>(), "testWhiteBag");
        testBlackBag = new Black(new ArrayList<Integer>(), "testBlackBag", testWhiteBag);
    }

    @After
    public void tearDown() throws Exception {
        testWhiteBag = null;
        testBlackBag = null;
        assertNull(testWhiteBag);
        assertNull(testBlackBag);
    }

    @Test
    public void testBagCreation(){
        assertEquals("Wrong black bag name created.", testBlackBag.getBagName(), "testBlackBag");
        assertEquals("Wrong bag selected.", testBlackBag.getBag(), new ArrayList<Integer>());
        assertEquals("Wrong white bag name created.", testWhiteBag.getBagName(), "testWhiteBag");
        assertEquals("Wrong bag selected.", testWhiteBag.getBag(), new ArrayList<Integer>());
    }

    @Test
    public void testGetLinkedBag(){
        assertEquals("Wrong linked bag.", testBlackBag.getLinkedBag(), testWhiteBag);
    }

    @Test
    public void testDrawPebble(){
        testBlackBag.addPebble(10);
        assertTrue("Pebble was not drawn from the bag.", testBlackBag.drawPebble() == 10);
    }

    @Test
    public void testBlackRefill(){
        testBlackBag.getLinkedBag().addPebble(10);
        testBlackBag.refillBag(testBlackBag.getBag(), testBlackBag.getLinkedBag());
        assertEquals("Black bag was not refilled.", testBlackBag.getSize(), 1);
    }

    @Test
    public void testWhiteCleared(){
        testBlackBag.getLinkedBag().addPebble(10);
        testBlackBag.refillBag(testBlackBag.getBag(), testBlackBag.getLinkedBag());
        assertEquals("White bag was not emptied.", testWhiteBag.getSize(), 0);
    }

}