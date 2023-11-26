
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Queue;

public class PackTest {
    private Pack testPack;

    @Before
    public void setUp() {
        Pack testPack = new Pack(4, "four.txt");
        this.testPack = testPack;
    }

    @Test
    public void testGetNumberOfPlayers() {
        int result = testPack.getNumberOfPlayers();
        assertNotEquals(0, result);
        testPack.distributeCards();
        int result2 = testPack.getNumberOfPlayers();
        assertEquals(4, result2);
    }

    @Test
    public void testGetPackFileName() {
        String result = testPack.getPackFileName();
        assertEquals("four.txt", result);
    }

    @Test
    public void testReadPack() {
        testPack.readPack(testPack.getPackFileName());
        Queue<Card> testPackCards = testPack.getPackCards();
        String n = "14";
        String n2 = "2";
        assertEquals(n, testPackCards.poll().getFaceValue());
        assertNotEquals(n2, testPackCards.poll().getFaceValue());
    }

    @Test
    public void testValidPack() {
        assertTrue(testPack.validPack(testPack.getNumberOfPlayers(), testPack.getPackFileName()));
    }

    @Test
    public void testNotValidPack() {
        Pack emptyPack = new Pack(4, "nullPack.txt");
        assertTrue(!Pack.validPack(emptyPack.getNumberOfPlayers(), emptyPack.getPackFileName()));
        Pack tooLargePack = new Pack(4, "tooLargePack.txt");
        assertTrue(!Pack.validPack(tooLargePack.getNumberOfPlayers(), tooLargePack.getPackFileName()));
        Pack noNumPack = new Pack(4, "tooLargePack.txt");
        assertTrue(!Pack.validPack(noNumPack.getNumberOfPlayers(), noNumPack.getPackFileName()));
    }

    public void testDistributePack() {
        testPack.distributeCards();
        int n = testPack.getNumberOfPlayers();

        // Tests for Player generation
        assertEquals(4, Player.getAllPlayers().size());
        for (int i = 1; i <= n; i++) {
            assertEquals(i, Player.getAllPlayers().get(i - 1).getPlayerNum());
        }

        // Tests for Deck generation
        assertEquals(4, Deck.getAllDecks().size());
        for (int i = 1; i <= n; i++) {
            assertEquals(i, Deck.getAllDecks().get(i - 1).getDeckNum());
        }
    }
}
