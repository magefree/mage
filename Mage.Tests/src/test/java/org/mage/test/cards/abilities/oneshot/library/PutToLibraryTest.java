package org.mage.test.cards.abilities.oneshot.library;

import java.util.ArrayList;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PutToLibraryTest extends CardTestPlayerBase {

    @Test
    public void testPutSecondFromTop() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Put target creature into its owner's library second from the top. Its controller gains 3 life.
        addCard(Zone.HAND, playerA, "Oust"); // Sorcery {W}

        addCard(Zone.BATTLEFIELD, playerB, "Dread Wanderer");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oust", "Dread Wanderer");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Oust", 1);
        assertLibraryCount(playerB, "Dread Wanderer", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 23);

        ArrayList<Card> cardArray = new ArrayList<>(playerB.getLibrary().getCards(currentGame));
        Assert.assertTrue("Library has no cards but should have", cardArray.size() > 1);
        Card secondCard = cardArray.get(1);
        Assert.assertTrue("Second card from top should be Dread Wanderer, but it isn't",
                secondCard != null && secondCard.getName().equals("Dread Wanderer"));

    }

    // Unexpectedly Absent doesn't work properly, no matter how much you pay for X the card is always returned on top of the library.
    @Test
    public void testUnexpectedlyAbsent() {
        // Flying
        // At the beginning of combat on your turn, you may pay {G}{U}. When you do, put a +1/+1 counter on another target creature you control, and that creature gains flying until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Skyrider Patrol", 1);

        // Put target nonland permanent into its owner's library just beneath the top X cards of that library.
        addCard(Zone.HAND, playerB, "Unexpectedly Absent"); // Instant {X}{W}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Unexpectedly Absent", "Skyrider Patrol");
        setChoice(playerB, "X=3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Unexpectedly Absent", 1);
        assertPermanentCount(playerA, "Skyrider Patrol", 0);
        assertLibraryCount(playerA, "Skyrider Patrol", 1);

        ArrayList<Card> cardArray = new ArrayList<>(playerA.getLibrary().getCards(currentGame));
        Assert.assertTrue("Library has no cards but should have", cardArray.size() > 3);
        Card fourthCard = cardArray.get(3);// get the 4th element
        Assert.assertTrue("Fourth card from top should be Skyrider Patrol, but it isn't",
                fourthCard != null && fourthCard.getName().equals("Skyrider Patrol"));

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
