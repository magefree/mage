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
public class ScryTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.Preordain} <br>
     * Preordain {U} <br>
     * Sorcery <br>
     * Scry 2, then draw a card. (To scry 2, look at the top two cards of your library, then put any number of them on the bottom and the rest on top in any order.)
     */
    private static String preordain = "Preordain";

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
    public void Scry2_BottomBottom() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, preordain);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, preordain);
        addTarget(playerA, cardA + "^" + cardB); // scrying both bottom
        setChoice(playerA, cardB); // cardB before cardA
        // + draw C

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, cardC, 1);
        assertLibrary(playerA, cardD, cardB, cardA);
    }

    @Test
    public void Scry2_BottomBottom_otherorder() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, preordain);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, preordain);
        addTarget(playerA, cardA + "^" + cardB); // scrying both bottom
        setChoice(playerA, cardA); // cardA before cardB
        // + draw C

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, cardC, 1);
        assertLibrary(playerA, cardD, cardA, cardB);
    }

    @Test
    public void Scry2_BottomTop() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, preordain);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, preordain);
        addTarget(playerA, cardA); // scrying A bottom
        // + draw B

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, cardB, 1);
        assertLibrary(playerA, cardC, cardD, cardA);
    }

    @Test
    public void Scry2_TopBottom() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, preordain);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, preordain);
        addTarget(playerA, cardB); // scrying B bottom
        // + draw A

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, cardA, 1);
        assertLibrary(playerA, cardC, cardD, cardB);
    }

    @Test
    public void Scry2_TopTop() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, preordain);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, preordain);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scrying both on top
        setChoice(playerA, cardB); // cardB below cardA
        // + draw A

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, cardA, 1);
        assertLibrary(playerA, cardB, cardC, cardD);
    }

    @Test
    public void Scry2_TopTop_otherorder() {
        setStrictChooseMode(true);
        initLibrary();

        addCard(Zone.HAND, playerA, preordain);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, preordain);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scrying both on top
        setChoice(playerA, cardA); // cardA below cardB
        // + draw B

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, cardB, 1);
        assertLibrary(playerA, cardA, cardC, cardD);
    }
}
