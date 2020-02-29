import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({BagTest.class, BlackTest.class, WhiteTest.class, PebbleGameTest.class})
public class PebbleGameTestSuite {

}