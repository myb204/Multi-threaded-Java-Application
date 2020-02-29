import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WhiteTest {

    private White testWhiteBag;

    @Before
    public void setUp() throws Exception {
        testWhiteBag = new White(new ArrayList<Integer>(), "testWhiteBag");
    }

    @After
    public void tearDown() throws Exception {
        testWhiteBag = null;
    }

    @Test
    public void testBagCreation(){
        assertEquals("Wrong bag name created.", testWhiteBag.getBagName(), "testWhiteBag");
        assertEquals("Wrong bag selected.", testWhiteBag.getBag(), new ArrayList<Integer>());
    }

}