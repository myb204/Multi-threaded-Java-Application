import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class PebbleGameTest {

    PebbleGame tGame;
    PebbleGame.Players tPlayers;

    @Before
    public void setUp() throws Exception {
        PebbleGame tGame = new PebbleGame();
    }

    @After
    public void tearDown() throws Exception {
        tGame = null;
        tPlayers = null;
        assertNull(tGame);
        assertNull(tPlayers);
    }

    @Test
    public void testInitializeBagX() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        PebbleGame testGame = new PebbleGame();
        Field blackBagField = Class.forName("PebbleGame").getDeclaredField("X");
        blackBagField.setAccessible(true);
        Black X = (Black) blackBagField.get(testGame);
        ArrayList<Integer> pebbleList1 = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++){
            pebbleList1.add(i);
        }

        testGame.initializeBag(X, pebbleList1, 4);

        assertTrue("Bag not properly initialized.", ((Black) blackBagField.get(testGame)).getBag().size() >= 44 );
    }

    @Test
    public void testInitializeBagY() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        PebbleGame testGame = new PebbleGame();
        Field blackBagField = Class.forName("PebbleGame").getDeclaredField("Y");
        blackBagField.setAccessible(true);
        Black Y = (Black) blackBagField.get(testGame);
        ArrayList<Integer> pebbleList1 = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++){
            pebbleList1.add(i);
        }

        testGame.initializeBag(Y, pebbleList1, 4);

        assertTrue("Bag not properly initialized.", ((Black) blackBagField.get(testGame)).getBag().size() >= 44 );
    }

    @Test
    public void testInitializeBagZ() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        PebbleGame testGame = new PebbleGame();
        Field blackBagField = Class.forName("PebbleGame").getDeclaredField("Z");
        blackBagField.setAccessible(true);
        Black Z = (Black) blackBagField.get(testGame);
        ArrayList<Integer> pebbleList1 = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++){
            pebbleList1.add(i);
        }

        testGame.initializeBag(Z, pebbleList1, 4);

        assertTrue("Bag not properly initialized.", ((Black) blackBagField.get(testGame)).getBag().size() >= 44 );
    }

    @Test
    public void testGameStops() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        PebbleGame testGame = new PebbleGame();
        Field gameOverField = Class.forName("PebbleGame").getDeclaredField("gameOver");
        gameOverField.setAccessible(true);
        AtomicBoolean gameOver = (AtomicBoolean) gameOverField.get(testGame);
        gameOverField.set(testGame, gameOver);
        testGame.stopGame();
        assertTrue("Game over was not set.", ((AtomicBoolean) gameOverField.get(testGame)).get() == true);

    }

    @Test
    public void testSelectBag() throws ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        Method selectBag = Class.forName("PebbleGame").getMethod("selectBag");
        Black value = (Black) selectBag.invoke(testGame);
        assertNotNull("Wrong bag selected.",  value);
    }

    @Test
    public void testValidWinningHand() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        PebbleGame.Players testPlayer = new PebbleGame.Players(testGame, 1, 1);
        Method winningHand = Class.forName("PebbleGame").getMethod("winningHand", ArrayList.class);
        Field playerHandField = Class.forName("PebbleGame$Players").getDeclaredField("playerHand");
        playerHandField.setAccessible(true);
        ArrayList<Integer> playerHand = (ArrayList<Integer>) playerHandField.get(testPlayer);
        playerHand.add(10);
        playerHand.add(90);
        boolean value = (boolean) winningHand.invoke(testGame, playerHand);
        assertTrue("Not a valid winning hand.", value);
    }

    @Test
    public void testNotValidWinningHand() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        PebbleGame.Players testPlayer = new PebbleGame.Players(testGame, 1, 1);
        Method winningHand = Class.forName("PebbleGame").getMethod("winningHand", ArrayList.class);
        Field playerHandField = Class.forName("PebbleGame$Players").getDeclaredField("playerHand");
        playerHandField.setAccessible(true);
        ArrayList<Integer> playerHand = (ArrayList<Integer>) playerHandField.get(testPlayer);
        playerHand.add(10);
        playerHand.add(80);
        boolean value = (boolean) winningHand.invoke(testGame, playerHand);
        assertTrue("Not a valid winning hand.", !value);
    }

    @Test
    public void testThreadStops() throws InterruptedException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        PebbleGame testGame = new PebbleGame();
        PebbleGame.Players testPlayer = new PebbleGame.Players(testGame, 1, 1);
        Field gameOverField = Class.forName("PebbleGame").getDeclaredField("gameOver");
        gameOverField.setAccessible(true);
        AtomicBoolean gameOver = (AtomicBoolean) gameOverField.get(testGame);
        gameOverField.set(testGame, gameOver);

        Thread playerThread = new Thread(testPlayer);
        playerThread.start();

        testGame.stopGame();

        assertTrue("Thread did not stop.", !playerThread.isInterrupted());
    }

    @Test
    public void testThreadRuns() throws InterruptedException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        PebbleGame testGame = new PebbleGame();
        PebbleGame.Players testPlayer = new PebbleGame.Players(testGame, 1, 1);

        Thread playerThread = new Thread(testPlayer);
        playerThread.start();

        assertTrue("Thread did not run.", playerThread.isAlive());
    }

    @Test
    public void testReadEmptyFile() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        ArrayList<Integer> pebbleList1 = (new ArrayList<Integer>());
        Method readFile = Class.forName("PebbleGame").getDeclaredMethod("readFile", ArrayList.class, String.class);
        Boolean returnValue = (Boolean) readFile.invoke(testGame, pebbleList1, "TestFiles/emptyfile.txt");
        assertFalse("File was read although empty.", (Boolean) returnValue);
    }

    @Test
    public void testReadCharactersFile() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        ArrayList<Integer> pebbleList1 = (new ArrayList<Integer>());
        Method readFile = Class.forName("PebbleGame").getDeclaredMethod("readFile", ArrayList.class, String.class);
        Boolean returnValue = (Boolean) readFile.invoke(testGame, pebbleList1, "TestFiles/lettersInFile.txt");
        assertFalse("File was read although has characters.", (Boolean) returnValue);
    }

    @Test
    public void testFileNotFound() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        ArrayList<Integer> pebbleList1 = (new ArrayList<Integer>());
        Method readFile = Class.forName("PebbleGame").getDeclaredMethod("readFile", ArrayList.class, String.class);
        Boolean returnValue = (Boolean) readFile.invoke(testGame, pebbleList1, "TestFiles/filePathNotHere.txt");
        assertFalse("File path threw no exception.", (Boolean) returnValue);
    }

    @Test
    public void testFileHasNegatives() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        ArrayList<Integer> pebbleList1 = (new ArrayList<Integer>());
        Method readFile = Class.forName("PebbleGame").getDeclaredMethod("readFile", ArrayList.class, String.class);
        Boolean returnValue = (Boolean) readFile.invoke(testGame, pebbleList1, "TestFiles/negativeNumbers.txt");
        assertFalse("File path threw no exception.", (Boolean) returnValue);
    }

    @Test
    public void testFileIsValid() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        ArrayList<Integer> pebbleList1 = (new ArrayList<Integer>());
        Method readFile = Class.forName("PebbleGame").getDeclaredMethod("readFile", ArrayList.class, String.class);
        Boolean returnValue = (Boolean) readFile.invoke(testGame, pebbleList1, "TestFiles/validFile.txt");
        assertFalse("File path threw exception.", (Boolean) !returnValue);
    }

    @Test
    public void testFileNotCSV() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        ArrayList<Integer> pebbleList1 = (new ArrayList<Integer>());
        Method readFile = Class.forName("PebbleGame").getDeclaredMethod("readFile", ArrayList.class, String.class);
        Boolean returnValue = (Boolean) readFile.invoke(testGame, pebbleList1, "TestFiles/notAValidCSV.txt");
        assertFalse("File path threw exception.", (Boolean) returnValue);
    }

    @Test
    public void testWriteSuccessful() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PebbleGame testGame = new PebbleGame();
        PebbleGame.Players testPlayer = new PebbleGame.Players(testGame, 1, 1);

        Field playerNumberField = Class.forName("PebbleGame$Players").getDeclaredField("playerNumber");
        playerNumberField.setAccessible(true);
        int playerNumber = (int) playerNumberField.get(testPlayer);

        Method writeTurn = Class.forName("PebbleGame").getDeclaredMethod("writeTurn", int.class, String.class, boolean.class);
        Boolean returnValue = (Boolean) writeTurn.invoke(testGame, playerNumber, "Turn 1", true);

        assertTrue("Turn was not written.", (Boolean) returnValue);
    }

    @Test
    public void testDraw() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        PebbleGame testGame = new PebbleGame();
        PebbleGame.Players testPlayer = new PebbleGame.Players(testGame, 1, 1);

        Field blackBagField = Class.forName("PebbleGame").getDeclaredField("X");
        blackBagField.setAccessible(true);
        Black X = (Black) blackBagField.get(testGame);
        X.addPebble(10);

        Field playerHandField = Class.forName("PebbleGame$Players").getDeclaredField("playerHand");
        playerHandField.setAccessible(true);
        ArrayList<Integer> playerHand = (ArrayList<Integer>) playerHandField.get(testPlayer);
        playerHand.add(10);

        testGame.draw(testPlayer);
        assertTrue("Pebble was not drawn from the bag.", ((ArrayList<Integer>)playerHandField.get(testPlayer)).size() == 2);
    }



}