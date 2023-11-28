import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    public Player testPlayer;

    @Before
    public void setUp() {
        Pack pack = new Pack(4, "four.txt");
        pack.distributeCards();
        Player testPlayer = Player.getAllPlayers().get(0);
        this.testPlayer = testPlayer;
    }

    @Test
    public void testPresentHand() {
        String sample = " 14 2 13 3";
        assertEquals(testPlayer.presentHand(), sample);
    }

    @Test
    public void testGetPlayerNum() {
        assertEquals(testPlayer.getPlayerNum(), 0);
    }

    @Test
    public void testGetPlayerHand() {
        ArrayList<Card> testHand = new ArrayList<>();
        Card card1 = new Card("14");
        Card card2 = new Card("2");
        Card card3 = new Card("13");
        Card card4 = new Card("3");
        testHand.add(card1);
        testHand.add(card2);
        testHand.add(card3);
        testHand.add(card4);
        assertEquals(testHand, testPlayer.getPlayerHand());
    }

    @Test
    public void testGetAllPlayers() {
        assertEquals(4, Player.getAllPlayers().size());
        assertEquals(4, Player.getAllPlayers().get(3).getPlayerNum());
    }

    @Test
    public void testAddToHand() {
        Card testCard = new Card("100");
        testPlayer.addToHand(testCard, 0);
        assertEquals("100", testPlayer.getPlayerHand().get(0).getFaceValue());
        testPlayer.removeCard(0);
    }

    // Test method to verify game has completed and closed
    @Test
    public void testGameCompletion() {
        for (int i = 0; i < Player.getAllPlayers().size(); i++) {
            Player player = Player.getAllPlayers().get(i);
            player.createOutputFile();
            player.startThread();
        }
        assertTrue(Player.stopFlag);
    }

    // Test method to verify game has completed and closed
    @Test
    public void testGameCompletion() {
        try {
            for (int i = 0; i < Player.getAllPlayers().size(); i++) {
                Player player = Player.getAllPlayers().get(i);
                player.createOutputFile();
                player.startThread();
            }
        } catch (Exception e) {
            assertTrue(false);
            System.out.println("Failure reached in game loop");
        } finally {
            assertTrue(Player.stopFlag);
        }
    }
}
