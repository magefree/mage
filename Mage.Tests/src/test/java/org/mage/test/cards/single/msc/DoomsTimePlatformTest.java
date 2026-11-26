package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DoomsTimePlatformTest extends CardTestPlayerBase {

    /**
     * Doom's Time Platform {4}
     * Artifact
     * Whenever you attack, exile target nonland card from your graveyard with two time counters on it. If it doesn't
     * have suspend, it gains suspend.
     */
    private static final String platform = "Doom's Time Platform";

    @Test
    public void test_ExileWithTwoTimeCounters() {
        addCard(Zone.BATTLEFIELD, playerA, platform);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");

        attack(1, playerA, "Grizzly Bears", playerB);
        addTarget(playerA, "Lightning Bolt"); // exile with time counters

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertExileCount(playerA, "Lightning Bolt", 1);
        assertCounterOnExiledCardCount("Lightning Bolt", CounterType.TIME, 2);
    }

    @Test
    public void test_GainedSuspend_CastWithoutPayingAndHaste() {
        addCard(Zone.BATTLEFIELD, playerA, platform);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // 2/2, no suspend

        // turn 1: exile with 2 time counters
        attack(1, playerA, "Silvercoat Lion", playerB);
        addTarget(playerA, "Grizzly Bears"); // exile with time counters

        // turn 3 upkeep: 2 -> 1, turn 5 upkeep: 1 -> 0 and cast it, it has haste
        setChoice(playerA, true); // cast it without paying its mana cost
        attack(5, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20 - 2 - 2); // lion + bears
    }

    @Test
    public void test_NoNonlandCardInYourGraveyard_NoTarget() {
        addCard(Zone.BATTLEFIELD, playerA, platform);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.GRAVEYARD, playerA, "Forest");
        //
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt");

        // only a land in your graveyard and a nonland card in the opponent's one, so no target to choose
        attack(1, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Forest", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }
}
