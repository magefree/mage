package org.mage.test.cards.abilities.oneshot.library;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author grimreap124
 */
public class SurveilXTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DesmondMiles} <br>
     */
    private static String desmondMiles = "Desmond Miles";

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
    public void SurveilX_one_Yard() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.BATTLEFIELD, playerA, desmondMiles);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        attack(1, playerA, desmondMiles, playerB);
        addTarget(playerA, cardA); // surveil both in graveyard
        // + draw C

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, cardA, 1);
        assertLibrary(playerA, cardB, cardC, cardD);
    }

    @Test
    public void SurveilX_two_Yard() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.BATTLEFIELD, playerA, desmondMiles);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Battlegrowth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth");
        addTarget(playerA, desmondMiles);

        attack(1, playerA, desmondMiles, playerB);
        addTarget(playerA, cardA + "^" + cardB); // surveil both in graveyard
        // + draw C

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, cardA, 1);
        assertGraveyardCount(playerA, cardB, 1);
        assertGraveyardCount(playerA, "Battlegrowth", 1);
        assertGraveyardCount(playerA, 3);
        assertLibrary(playerA, cardC, cardD);
    }


}
