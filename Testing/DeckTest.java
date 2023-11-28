//import all the required component for testing 
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeckTest {
    // initiate the private Deck variables 
    private Deck testDeck;
    private Deck sourceDeck;
    private Deck destinationDeck;

    @Before
    public void setUp() {
        // set up 3 decks for testing manipulation 
        testDeck = new Deck(0);
        sourceDeck = new Deck(1);
        destinationDeck = new Deck(2);
    }

    @Test
    public void testAddToDeck() {
        Card testCard = new Card("9"); 
        // Adding a card to the deck
        testDeck.addToDeck(testCard);
        // Checking if the deck content contains the added card
        assertTrue(testDeck.getDeckContent().contains(testCard));
    }

    @Test
    public void testDrawCard() {
        Card testCard = new Card("2");
        testDeck.addToDeck(testCard);
        // Drawing a card from the deck
        Card drawnCard = testDeck.drawCard();
        // Checking if the drawn card is the same as the added card
        assertEquals(testCard, drawnCard);
    }

    @Test
    public void testGetDeckNum() {
        // Checking if the deck number is initialized correctly
        assertEquals(0, testDeck.getDeckNum());
    }

    @Test
    public void testGetAllDecks() {
        // Checking if the static list of all decks contains the test deck
        assertTrue(Deck.getAllDecks().contains(testDeck));
    }
    @Test
    public void testDrawCardAndAddToAnotherDeck() {
        // Adding cards to the source deck
        Card card1 = new Card("9");
        Card card2 = new Card("2");
        sourceDeck.addToDeck(card1);
        sourceDeck.addToDeck(card2);

        // Drawing a card from the source deck
        Card drawnCard = sourceDeck.drawCard();
        // Adding the drawn card to the destination deck
        destinationDeck.addToDeck(drawnCard);

        // Checking the source deck content after drawing a card
        assertFalse(sourceDeck.getDeckContent().contains(drawnCard));

        // Checking the destination deck content after adding the drawn card
        assertTrue(destinationDeck.getDeckContent().contains(drawnCard));
    }

    @Test
    public void testMultipleDecksInteraction() {
        // Adding cards to both decks
        Card card1 = new Card("3");
        Card card2 = new Card("4");
        Card card3 = new Card("5");
        sourceDeck.addToDeck(card1);
        sourceDeck.addToDeck(card2);
        destinationDeck.addToDeck(card3);

        // Drawing a card from the source deck
        Card drawnCard = sourceDeck.drawCard();
        // Adding the drawn card to the destination deck
        destinationDeck.addToDeck(drawnCard);

        // Checking the source deck content after drawing a card
        assertFalse(sourceDeck.getDeckContent().contains(drawnCard));

        // Checking the destination deck content after adding the drawn card
        assertTrue(destinationDeck.getDeckContent().contains(drawnCard));

        // Checking if all decks are present in the static list of all decks
        assertTrue(Deck.getAllDecks().contains(sourceDeck));
        assertTrue(Deck.getAllDecks().contains(destinationDeck));
    }
}
