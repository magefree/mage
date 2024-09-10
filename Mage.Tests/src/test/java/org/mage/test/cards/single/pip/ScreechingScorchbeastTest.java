package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ScreechingScorchbeastTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.ScreechingScorchbeast Screeching Scorchbeast} {4}{B}{B}
     * Creature — Bat Mutant
     * Flying, menace
     * Whenever Screeching Scorchbeast attacks, each player gets two rad counters.
     * Whenever one or more nonland cards are milled, you may create that many 2/2 black Zombie Mutant creature tokens. Do this only once each turn.
     * 5/5
     */
    private static final String beast = "Screeching Scorchbeast";

    @Test
    public void test_Trigger_3NonLand_1Land() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, beast);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Whetstone"); // {3}: Each player mills two cards.
        addCard(Zone.LIBRARY, playerA, "Taiga", 3);
        addCard(Zone.LIBRARY, playerA, "Baneslayer Angel", 1);
        addCard(Zone.LIBRARY, playerB, "Memnite", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setChoice(playerA, true); // Yes to first trigger.
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        // The second mill does not trigger the Scrochbeast due to "Do this once each turn"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Taiga", 3);
        assertGraveyardCount(playerA, "Baneslayer Angel", 1);
        assertGraveyardCount(playerB, "Memnite", 4);
        assertPermanentCount(playerA, "Zombie Mutant Token", 3);
    }

    @Test
    public void test_Trigger_No_ThenYes() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, beast);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Whetstone"); // : Each player mills two cards.
        addCard(Zone.LIBRARY, playerA, "Taiga", 3);
        addCard(Zone.LIBRARY, playerA, "Baneslayer Angel", 1);
        addCard(Zone.LIBRARY, playerB, "Memnite", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setChoice(playerA, false); // No to first trigger. 3 nonlands
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setChoice(playerA, true); // Yes to second trigger. 2 nonlands

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Taiga", 3);
        assertGraveyardCount(playerA, "Baneslayer Angel", 1);
        assertGraveyardCount(playerB, "Memnite", 4);
        assertPermanentCount(playerA, "Zombie Mutant Token", 2);

    }

    @Test
    public void test_NoTrigger_AllLands() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, beast);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Whetstone"); // {3}: Each player mills two cards.
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        // no trigger, no choice.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerB, 2);
        assertPermanentCount(playerA, "Zombie Mutant Token", 0);
    }
}
