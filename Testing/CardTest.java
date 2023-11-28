//import all the required component for testing 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class CardTest {
    // initiate private Card variable 
    private Card testCard;

    @Before
    public void setUp() {
        //seting up a new card of value 12
        Card testCard = new Card("12");
        this.testCard = testCard;
    }

    @Test
    public void testFaceValue(){
        //testing if the value coresponds to the created card with asssertions 
        assertNotEquals("9",testCard.getFaceValue());
        assertEquals("12", testCard.getFaceValue());
    }
}
