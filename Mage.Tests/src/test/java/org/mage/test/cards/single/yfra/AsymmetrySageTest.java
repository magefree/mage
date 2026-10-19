package org.mage.test.cards.single.yfra;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AsymmetrySageTest extends CardTestPlayerBase {

    @Test
    public void testBasePowerAndDuration() {
        addCard(Zone.BATTLEFIELD, playerA, "Asymmetry Sage");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Giant Growth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears");
        addTarget(playerA, "Grizzly Bears");
        checkPT("base power plus Growth", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 6, 5);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
        assertPowerToughness(playerA, "Asymmetry Sage", 0, 2);
    }

    @Test
    public void testCopiedSpellTriggersMagecraft() {
        addCard(Zone.BATTLEFIELD, playerA, "Asymmetry Sage");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Reverberate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        addTarget(playerA, "Asymmetry Sage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reverberate", "Cast Shock", "Cast Shock");
        addTarget(playerA, "Grizzly Bears");
        setChoice(playerA, false); // Keep the copied Shock's target.
        addTarget(playerA, "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Asymmetry Sage", 3, 2);
        assertPowerToughness(playerA, "Grizzly Bears", 3, 2);
        assertPowerToughness(playerA, "Goblin Piker", 3, 1);
        assertLife(playerB, 16);
    }
}
