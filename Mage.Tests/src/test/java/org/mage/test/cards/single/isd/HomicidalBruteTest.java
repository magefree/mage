package org.mage.test.cards.single.isd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HomicidalBruteTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        addCard(Zone.LIBRARY, playerA, "Sejiri Merfolk");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Civilized Scholar", 0);
        assertPermanentCount(playerA, "Homicidal Brute", 1);
        assertTapped("Homicidal Brute", false);
    }

    @Test
    public void testCardNegative() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Civilized Scholar", 1);
        assertTapped("Civilized Scholar", true);
        assertPermanentCount(playerA, "Homicidal Brute", 0);
    }

    @Test
    public void testCardTransform() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        addCard(Zone.LIBRARY, playerA, "Sejiri Merfolk");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Civilized Scholar", 1);
        assertTapped("Civilized Scholar", true);
        assertPermanentCount(playerA, "Homicidal Brute", 0);
    }

    @Test
    public void testCardNotTransform() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        addCard(Zone.LIBRARY, playerA, "Sejiri Merfolk", 2);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        attack(3, playerA, "Homicidal Brute");
        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15);
        assertPermanentCount(playerA, "Civilized Scholar", 0);
        assertPermanentCount(playerA, "Homicidal Brute", 1);
        assertTapped("Homicidal Brute", true);
    }

    @Test
    public void testCardBlinkNotTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        addCard(Zone.HAND, playerA, "Sejiri Merfolk");
        addCard(Zone.HAND, playerA, "Moonmist"); // transform all
        addCard(Zone.HAND, playerA, "Cloudshift"); // blink
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 3);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        setChoice(playerA, "Sejiri Merfolk"); // discard creature

        attack(3, playerA, "Homicidal Brute", playerB);
        castSpell(3, PhaseStep.COMBAT_DAMAGE, playerA, "Cloudshift", "Homicidal Brute");
        castSpell(3, PhaseStep.END_COMBAT, playerA, "Moonmist");

        checkPermanentTapped("after transform", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Homicidal Brute", false, 1);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertGraveyardCount(playerA, "Moonmist", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 15);
        assertPermanentCount(playerA, "Civilized Scholar", 1);
        assertPermanentCount(playerA, "Homicidal Brute", 0);
        assertTapped("Civilized Scholar", true);
    }

}
