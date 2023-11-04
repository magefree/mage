package org.mage.test.cards.abilities.oneshot.library;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;

/**
 * @author Susucr
 */
public class SurveilTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.Curate} <br>
     * Curate {U} <br>
     * Instant <br>
     * Surveil 2. (Look at the top two cards of your library, then put any number of them into your graveyard and the rest on top of your library in any order.) <br>
     * Draw a card.
     */
    private static String curate = "Curate";

    private static String cardD = "Devilthorn Fox";
    private static String cardC = "Canopy Gorger";
    private static String cardB = "Barbtooth Wurm";
    private static String cardA = "Alaborn Trooper";

    private void initLibrary() {
        // make a library of 4 cards, bottom : D C B A : top
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, cardD);
        addCard(Zone.LIBRARY, playerA, cardC);
        addCard(Zone.LIBRARY, playerA, cardB);
        addCard(Zone.LIBRARY, playerA, cardA);
    }

    /**
     * assert that the library is exactly the cards provided in argument
     */
    private void assertLibrary(TestPlayer player, String... cards) {
        List<Card> library = player.getLibrary().getCards(currentGame);
        Assert.assertEquals("Library of " + player.getName() + " is not of the expected size", cards.length, library.size());
        for (int i = 0; i < cards.length; i++) {
            Assert.assertEquals("Library of " + player.getName() + " has different card #" + i, cards[i], library.get(i).getName());
        }
    }

    @Test
    public void Surveil2_YardYard() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, curate);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curate);
        addTarget(playerA, cardA + "^" + cardB); // surveil both in graveyard
        // + draw C

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, cardA, 1);
        assertGraveyardCount(playerA, cardB, 1);
        assertHandCount(playerA, cardC, 1);
        assertLibrary(playerA, cardD);
    }

    @Test
    public void Surveil2_YardTop() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, curate);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curate);
        addTarget(playerA, cardA); // surveil A in graveyard
        // + draw B

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, cardA, 1);
        assertHandCount(playerA, cardB, 1);
        assertLibrary(playerA, cardC, cardD);
    }

    @Test
    public void Surveil2_TopYard() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, curate);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curate);
        addTarget(playerA, cardB); // surveil B in graveyard
        // + draw A

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, cardA, 1);
        assertGraveyardCount(playerA, cardB, 1);
        assertLibrary(playerA, cardC, cardD);
    }

    @Test
    public void Surveil2_TopTop() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, curate);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curate);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // surveil both on top
        setChoice(playerA, cardB); // cardB below cardA
        // + draw A

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, cardA, 1);
        assertLibrary(playerA, cardB, cardC, cardD);
    }

    @Test
    public void Surveil2_TopTop_otherorder() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, curate);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curate);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // surveil both on top
        setChoice(playerA, cardA); // cardA below cardB
        // + draw B

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, cardB, 1);
        assertLibrary(playerA, cardA, cardC, cardD);
    }
}
