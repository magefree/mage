package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Alex-Vasile
 */
public class CasualtyTest extends CardTestPlayerBase {

    // Instant
    // {1}{U}
    // Casualty 1
    // Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.
    private static final String aLittleChat = "A Little Chat";
    // Planeswalker
    // {1}{B}{R}
    // Casualty X
    // The copy isn’t legendary and has starting loyalty X.
    // −7: Target player draws seven cards and loses 7 life.
    private static final String obNixilisTheAdversary = "Ob Nixilis, the Adversary";
    // 7/7 used as casualty
    private static final String aetherwindBasker = "Aetherwind Basker";

    /**
     * Test Casualty on sorcery/instant.
     */
    @Test
    public void testCasualtySorceryInstant() {
        addCard(Zone.BATTLEFIELD, playerA, aetherwindBasker);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, aLittleChat);
        addCard(Zone.LIBRARY, playerA, "Desert", 4);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aLittleChat);
        setChoice(playerA, "Yes");
        setChoice(playerA, aetherwindBasker);
        addTarget(playerA, "Desert");
        addTarget(playerA, "Desert");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertHandCount(playerA, "Desert", 2);
        assertGraveyardCount(playerA, aetherwindBasker, 1);
    }

    /**
     * Test that casualty will only let you pay it once.
     */
    @Test
    public void testCanOnlyPayCasualtyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, aetherwindBasker, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, aLittleChat);
        addCard(Zone.LIBRARY, playerA, "Desert", 4);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aLittleChat);
        setChoice(playerA, "Yes");
        setChoice(playerA, aetherwindBasker);
        // If a second target was possible, it would have prompted us for another and this test would fail when strict choose mode was on
        addTarget(playerA, "Desert");
        addTarget(playerA, "Desert");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Desert", 2);
        assertGraveyardCount(playerA, aetherwindBasker, 1);
        assertPermanentCount(playerA, aetherwindBasker, 1);
    }

    /**
     * Test Casualty on a creature.
     * Test variable casualty.
     */
    @Test
    public void testVariableCasualtyOnCreature() {
        addCard(Zone.BATTLEFIELD, playerA, aetherwindBasker);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, obNixilisTheAdversary);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, obNixilisTheAdversary);
        setChoice(playerA, aetherwindBasker);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertGraveyardCount(playerA, aetherwindBasker, 1);
        assertPermanentCount(playerA, obNixilisTheAdversary, 2);  // 2 were created, but the token died when using its -7 ability

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-7:");  // -7 life and draw 7 cards
        addTarget(playerA, playerA);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, obNixilisTheAdversary, 1);
        assertLife(playerA, 20 - 7);
        assertHandCount(playerA, 7);
    }
}
