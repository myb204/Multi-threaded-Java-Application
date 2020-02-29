import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The PebbleGame program simulates a game that has multiple threads acting as players running
 * concurrently. Players draw and discard 'pebbles' from bags until their hand weight is equal
 * to the value of 100.
 *
 * @author 680031640 680039562
 */

public class PebbleGame {

    /**
     * This is the main method. This is where the number of players and the file locations are entered and
     * checked. An instance of the game is created and this is passed as an final attribute for each player thread.
     *
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException{

        final PebbleGame game = new PebbleGame();

        ArrayList<Integer> pebbleList1 = new ArrayList<Integer>();
        ArrayList<Integer> pebbleList2 = new ArrayList<Integer>();
        ArrayList<Integer> pebbleList3 = new ArrayList<Integer>();

        System.out.println("Welcome to the PebbleGame.");
        System.out.println("You will be asked for the location of three files.");
        System.out.println("The integer values must be comma separated and strictly positive.");
        System.out.println("The game will be simulated, and the output written to each players file in this directory.\n");

        System.out.println("Please enter the number of player: ");
        Scanner input = new Scanner(System.in);
        int n = 0;

        while (input.hasNext()){ // Returns true while the scanner has a input.
            if (input.hasNextInt()){
                n = input.nextInt();
                if (!(n <= 1 || n > 20)){
                    System.out.println("You selected " + n + " players.\n");
                    break;
                } else {
                    System.out.println("Invalid player number (maximum 20 players): ");
                }
            } else {
                if (input.next().equalsIgnoreCase("E")){
                    System.out.println("Exiting game...");
                    System.exit(0);
                } else {
                    System.out.println("Please enter the number of player: ");
                }
            }
        }

        System.out.println("Please enter the location of bag number 0 to load: ");
        while(!(game.readFile(pebbleList1, input.next()))) {} // Continually asks for a valid csv file.
        game.initializeBag(game.X, pebbleList1, n);

        System.out.println("Please enter the location of bag number 1 to load: ");
        while(!(game.readFile(pebbleList2, input.next()))) {}
        game.initializeBag(game.Y, pebbleList2, n);

        System.out.println("Please enter the location of bag number 2 to load: ");
        while(!(game.readFile(pebbleList3, input.next()))) {}
        game.initializeBag(game.Z, pebbleList3, n);


        System.out.println("Game is being simulated...");

        for (int x = 1; x <= n; x++) {
            Players player = new Players(game, x, n); // Creates a player object with the game instance, current state and max players.
            Thread playerThread = new Thread(player);
            playerThread.start();
        }

        Timer gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.stopGame();
                System.out.println("Game time limit reached: 1 Minutes");
                System.out.println("All player turns written to player output files.");
                System.out.println("No winner declared, exiting game...");
                System.exit(0);
            }
        }, 60*1000);
    }

    private Random generateID = new Random();
    private final int gameID = (100000 + generateID.nextInt(900000));
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private AtomicBoolean gameOver = new AtomicBoolean(false);

    /**
     * This HashMap stores the playerNumber and the Black Bag they selected, enabling
     * them to obtain the linked White Bag.
     */

    private HashMap<Integer, Black> map = new HashMap<>();

    private White A = new White(new ArrayList<Integer>(), "A");
    private White B = new White(new ArrayList<Integer>(), "B");
    private White C = new White(new ArrayList<Integer>(), "C");
    private Black X = new Black(new ArrayList<Integer>(), "X", A);
    private Black Y = new Black(new ArrayList<Integer>(), "Y", B);
    private Black Z = new Black(new ArrayList<Integer>(), "Z", C);

    /**
     * The readFile method reads the csv file entered by the user and adds each
     * integer into an ArrayList (pebbleList).
     *
     * @param pebbleList The ArrayList where the contents of the csv file are read to.
     * @param fileName The String value is the file location.
     * @return boolean This returns true if the file is valid, otherwise false.
     */

    public boolean readFile(ArrayList<Integer> pebbleList, String fileName){
        try {
            if (fileName.equalsIgnoreCase("E")){
                System.out.println("Exiting game...");
                System.exit(0);
            }

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            if (!(line == null)) {
                String[] S = line.split(",");
                for (String s: S){
                    try{
                        if (!(Integer.parseInt(s) <= 0) && s != ""){
                            pebbleList.add(Integer.parseInt(s));
                        } else {
                            System.out.println("Illegal file contents: file must contain positive integers.");
                            pebbleList.clear(); // Clear bag to prevent contamination.
                            return false;
                        }
                    }catch (NumberFormatException ex){
                        System.out.println("Illegal file contents: file must contain integers separated with commas.");
                        pebbleList.clear(); // Clear bag to prevent contamination.
                        return false;
                    }
                }
            } else {
                System.out.print("File is empty: file must contain positive integers separated with commas.");
                pebbleList.clear(); // Clear bag to prevent contamination.
                return false;
            }
            reader.close();
            return true;
        }catch (IOException ex) {
            System.out.println("File not found: enter a legal file format.");
            return false;
        }
    }

    /**
     * This method will write a turn into the players corresponding output file. It uses the
     * current player object's playerNumber attribute to write the players draw and discard turns
     * into their files.
     *
     * @param playerNumber The int value enables the turn to be written to the player's file location.
     * @param turn The String value is a description of the turn.
     * @param append The boolean value is set to false to clear the file before the game and true to append the turns.
     * @return boolean This returns true if the turn was successfully written, otherwise false.
     */

    public boolean writeTurn(int playerNumber, String turn, boolean append){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data\\player" + playerNumber + "_output.txt", append)); // Writes the output to the data folder.
            writer.append(turn + "\n");
            writer.close();
            return true;
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    /**
     * This method takes an empty black bag object and the ArrayList containing the list of
     * integers read from the csv file. It reads the integers into the black bag ensuring
     * there are at least 11 pebbles per player in each black bag; therefore initialising each
     * black bag.
     *
     * @param blackBag The Bag object where the pebbles are added to from the pebble list.
     * @param pebbleList The ArrayList containing the pebbles read from the csv file
     * @param maxPlayers The int value containing the number of players
     */

    public void initializeBag(Black blackBag, ArrayList<Integer> pebbleList, int maxPlayers){
        while(blackBag.getSize() <= (maxPlayers * 11)){
            for(Integer pebble : pebbleList){
                blackBag.addPebble(pebble);
            }
        }
    }

    /**
     * This method selects a bag that is not empty and draws a pebble and adds it to the active player's hand.
     *
     * @param activePlayer
     */

    public void draw(Players activePlayer){
        Black selectedBag;
        int pebble;

        do {
            selectedBag = selectBag();
        } while (selectedBag.getSize() == 0);

        synchronized (selectedBag){
            pebble = selectedBag.drawPebble();
            activePlayer.playerHand.add(pebble);
            map.put(activePlayer.playerNumber, selectedBag);

            if (selectedBag.getSize() == 0){
                selectedBag.refillBag(selectedBag.getBag(), selectedBag.getLinkedBag());
            }
        }

        map.put(activePlayer.playerNumber, selectedBag);
        writeTurn(activePlayer.playerNumber,"Player: " + activePlayer.playerNumber + " has drawn a " + pebble + " from bag " + selectedBag.getBagName(), true);
        writeTurn(activePlayer.playerNumber,"Player: " + activePlayer.playerNumber + " hand is " + activePlayer.playerHand,true);
    }

    /**
     * This method draws a pebble from the player's hand randomly and discards it into the linked white bag
     * of the black bag the player last selected.
     *
     * @param activePlayer
     */

    public void discard(Players activePlayer){
        White discardTo;
        int pebble;

        discardTo = map.get(activePlayer.playerNumber).getLinkedBag();
        Random random = new Random();
        int index = random.nextInt(activePlayer.playerHand.size());
        pebble = activePlayer.playerHand.get(index);
        activePlayer.playerHand.remove(index);

        synchronized (discardTo){
            discardTo.addPebble(pebble);
        }

        writeTurn(activePlayer.playerNumber,"Player: " + activePlayer.playerNumber + " has discarded a " + pebble + " to bag " + map.get(activePlayer.playerNumber).getLinkedBag().getBagName(),true);
        writeTurn(activePlayer.playerNumber,"Player: " + activePlayer.playerNumber + " hand is " + activePlayer.playerHand,true);
    }

    /**
     * This method takes a turn in the game. A player object is sent as the parameter and it
     * draws and discards pebbles from a bag before checking for a winning hand.
     *
     * @param activePlayer The player object containing the player's attributes.
     */

    public void takeTurn(Players activePlayer){

        if (activePlayer.playerHand.size() == 0){
            writeTurn(activePlayer.playerNumber, "GAME LOG " + dtf.format(LocalDateTime.now()) + " ID: " + gameID + "\n", false);
            for (int i = 0; i < 10; i++){
                draw(activePlayer);
            }
        } else {
            writeTurn(activePlayer.playerNumber, " ", true);
            writeTurn(activePlayer.playerNumber, "Round " + activePlayer.round, true);
            writeTurn(activePlayer.playerNumber, "Player: " + activePlayer.playerNumber + " hand is " + activePlayer.playerHand, true);

            discard(activePlayer);
            draw(activePlayer);

            activePlayer.round +=1;

            if (winningHand(activePlayer.playerHand)){
                if (!gameOver.get()){
                    stopGame();
                    writeTurn(activePlayer.playerNumber,"Player " + activePlayer.playerNumber + " " + " has won the game.",true);
                    System.out.println("Game over! All turns have been written to the output files.");
                }
            }

        }


    }

    /**
     * This method generates a random integer between 0 (inclusive) to 3(exclusive),
     * which is then used to select one of the three random black bags.
     *
     * @return selectedBag This is the randomly selected black bag object.
     */

    public Black selectBag(){
        Random random = new Random();
        int index = random.nextInt(3);
        Black selectedBag = null;

        switch (index){
            case 0:
                selectedBag = X;
                break;
            case 1:
                selectedBag = Y;
                break;
            case 2:
                selectedBag = Z;
                break;
        }

        return selectedBag;
    }

    /**
     * This method sums the values of the activePlayer's hand and checks if it equal to
     * 100, whereby it will return true, otherwise false.
     *
     * @param playerHand This is an ArrayList of integers containing the player's hand.
     * @return boolean This boolean returns true if the player has a winning hand, otherwise false.
     */

    public synchronized boolean winningHand(ArrayList<Integer> playerHand){

        int totalHand = 0;

        for (int pebble : playerHand){
            totalHand += pebble;
        }

        if (totalHand == 100){
            return true;
        }

        return false;
    }

    /**
     * This method is used to set the Atomic Boolean which is read by threads to true,
     * therefore causing threads to exit the runnable state.
     */

    public void stopGame(){
        gameOver.set(true);
    }

    /**
     * This is a static nested class which implements Runnable. Player objects are created in the main thread and
     * the overridden run method is called which is inside this static nested class.
     */

    public static class Players implements Runnable {

        private PebbleGame game;
        private int playerNumber;
        private int maxPlayers;
        private int round = 1;
        private ArrayList<Integer> playerHand = new ArrayList<Integer>();

        /**
         * This creates a player object with the instance of the game, the player number which
         * uniquely identifies each player, the maximum number of players and the player's currently
         * empty hand.
         *
         * @param game         This game object is the instance of the game.
         * @param playerNumber This int value is the players unique number.
         * @param maxPlayers   This int value is the maximum number of players in the game.
         */

        public Players(PebbleGame game, int playerNumber, int maxPlayers) {
            this.game = game;
            this.playerNumber = playerNumber;
            this.maxPlayers = maxPlayers;
            this.playerHand = playerHand; // Gives the player object an attribute for their hand.
            this.round = round;
        }

        /**
         * This method is run concurrently by all player threads when they are started in the main thread. This method's
         * logic runs while the game is in play.
         */

        @Override
        public void run() { // This method needs to be declared as public otherwise it will not be overridden.
            while (!game.gameOver.get()) {
                game.takeTurn(this);
            }
        }
    }
}
