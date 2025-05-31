package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class Vault13DwellersJourneyTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.Vault13DwellersJourney Vault 13: Dweller's Journey} {3}{W}
     * Enchantment — Saga
     * I — For each player, exile up to one other target enchantment or creature that player controls until this Saga leaves the battlefield.
     * II — You gain 2 life and scry 2.
     * III — Return two cards exiled with this Saga to the battlefield under their owners’ control and put the rest on the bottom of their owners’ libraries.
     */
    private static final String vault = "Vault 13: Dweller's Journey";

    @Test
    public void test_SimplePlay_Return() {
        addCard(Zone.HAND, playerA, vault, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vault);
        addTarget(playerA, "Memnite");
        addTarget(playerA, "Ornithopter");

        checkExileCount("after I: Memnite exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);
        checkExileCount("after I: Ornithopter exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ornithopter", 1);

        // turn 3
        setChoice(playerA, "Mountain"); // for Scry 2

        // turn 5
        checkExileCount("before III: Memnite exiled", 5, PhaseStep.UPKEEP, playerA, "Memnite", 1);
        checkExileCount("before III: Ornithopter exiled", 5, PhaseStep.UPKEEP, playerB, "Ornithopter", 1);

        setChoice(playerA, "Memnite^Ornithopter");

        setStrictChooseMode(true);
        setStopAt(8, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerB, "Ornithopter", 1);
        assertLife(playerA, 20 + 2);
    }

    @Test
    public void test_SimplePlay_NoReturn() {
        addCard(Zone.HAND, playerA, vault, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vault);
        addTarget(playerA, "Ornithopter");

        checkExileCount("after I: Ornithopter exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ornithopter", 1);

        // turn
        setChoice(playerA, "Mountain"); // for Scry 2

        // turn 5
        checkExileCount("before III: Ornithopter exiled", 5, PhaseStep.UPKEEP, playerB, "Ornithopter", 1);

        setStrictChooseMode(true);
        setStopAt(8, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerB, "Ornithopter", 1);
        assertLife(playerA, 20 + 2);
    }
}
