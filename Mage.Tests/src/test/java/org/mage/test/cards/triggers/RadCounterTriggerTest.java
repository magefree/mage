package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * > 725.1. Rad counters are a kind of counter a player can have (see rule 122, "Counters").
 * There is an inherent triggered ability associated with rad counters. This ability has no
 * source and is controlled by the active player. This is an exception to rule 113.8. The full
 * text of this ability is "At the beginning of each player's precombat main phase, if that
 * player has one or more rad counters, that player mills a number of cards equal to the number
 * of rad counters they have. For each nonland card milled this way, that player loses 1 life
 * and removes one rad counter from themselves."
 *
 * @author Susucr
 */
public class RadCounterTriggerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.n.NuclearFallout} {X}{B}{B}
     * Sorcery
     * Each creature gets twice -X/-X until end of turn. Each player gets X rad counters.
     */
    private static final String fallout = "Nuclear Fallout";

    private static void checkRadCounterCount(String message, Player player, int expected) {
        Assert.assertEquals(message, expected, player.getCounters().getCount(CounterType.RAD));
    }

    private static void checkGraveyardSize(String message, Player player, int expected) {
        Assert.assertEquals(message, expected, player.getGraveyard().size());
    }

    @Test
    public void test_Fallout_3_Multiple_Turns() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, fallout);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //addCard(Zone.BATTLEFIELD, playerB, "Blinkmoth Urn");

        addCard(Zone.BATTLEFIELD, playerA, "Yawgmoth's Bargain"); // Skip draw step, just to simplify library setup.
        addCard(Zone.BATTLEFIELD, playerB, "Yawgmoth's Bargain"); // Skip draw step, just to simplify library setup.

        // Library setup for player A:
        // Turn 7, 1 rad counter, mill 1 land, final check: 1 rad counter
        addCard(Zone.LIBRARY, playerA, "Tundra");
        // Turn 5, 3 rad counters, mill 1 land 2 nonland
        addCard(Zone.LIBRARY, playerA, "Akoum Warrior");
        addCard(Zone.LIBRARY, playerA, "Tropical Island");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        // Turn 3, 3 rad counters, mill only lands
        addCard(Zone.LIBRARY, playerA, "Bayou", 3);

        // Library setup for player B:
        // Turn 6, 0 rad counter, no trigger.
        // Turn 4, 2 rad counters, mill 2 nonlands
        addCard(Zone.LIBRARY, playerB, "Elite Vanguard");
        addCard(Zone.LIBRARY, playerB, "Fire // Ice");
        // Turn 2, 3 rad counters, mill 2 lands, 1 nonland
        addCard(Zone.LIBRARY, playerB, "Brainstorm");
        addCard(Zone.LIBRARY, playerB, "Plains", 2);

        setChoice(playerA, "X=3");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fallout);

        runCode("rad count playerA turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounterCount(info, player, 3));
        runCode("rad count playerB turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, (info, player, game) -> checkRadCounterCount(info, player, 3));

        // Turn 2 -- start 0 gy, 3 rad, mill 1 nonland 2 land
        runCode("rad count playerB turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, (info, player, game) -> checkRadCounterCount(info, player, 2));
        checkLife("life playerB turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, 19);
        runCode("graveyard count playerB turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, (info, player, game) -> checkGraveyardSize(info, player, 3));

        // Turn 3 -- start 1 gy, 3 rad, mill 3 nonland
        runCode("rad count playerA turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounterCount(info, player, 3));
        checkLife("life playerA turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 20);
        runCode("graveyard count playerA turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkGraveyardSize(info, player, 4));

        // Turn 4 -- start 3gy, 2 rad, mill 2 nonland
        runCode("rad count playerB turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, (info, player, game) -> checkRadCounterCount(info, player, 0));
        checkLife("life playerB turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, 17);
        runCode("graveyard count playerB turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, (info, player, game) -> checkGraveyardSize(info, player, 5));

        // Turn 5 -- start 4 gy, 3 rad, mill 2 nonland 1 land
        runCode("rad count playerA turn 5", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounterCount(info, player, 1));
        checkLife("life playerA turn 5", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, 18);
        runCode("graveyard count playerA turn 5", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkGraveyardSize(info, player, 7));

        // Turn 6 -- start 5gy, no rad
        runCode("rad count playerB turn 6", 6, PhaseStep.POSTCOMBAT_MAIN, playerB, (info, player, game) -> checkRadCounterCount(info, player, 0));
        checkLife("life playerB turn 6", 6, PhaseStep.POSTCOMBAT_MAIN, playerB, 17);
        runCode("graveyard count playerB turn 6", 6, PhaseStep.POSTCOMBAT_MAIN, playerB, (info, player, game) -> checkGraveyardSize(info, player, 5));

        // Turn 7 -- start 7 gy, 1 rad, mill 1 land
        runCode("rad count playerA turn 7", 7, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkRadCounterCount(info, player, 1));
        checkLife("life playerA turn 7", 7, PhaseStep.POSTCOMBAT_MAIN, playerA, 18);
        runCode("graveyard count playerA turn 7", 7, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkGraveyardSize(info, player, 8));

        setStopAt(7, PhaseStep.END_TURN);
        execute();
    }
}
