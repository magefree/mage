package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TombstoneStairwellTest extends CardTestPlayerBase {

    @Test
    public void test_LeavesTheBattlefield() {
        addCustomEffect_TargetDestroy(playerA);

        // Cumulative upkeep {1}{B} (At the beginning of your upkeep, put an age counter on this permanent,
        // then sacrifice it unless you pay its upkeep cost for each age counter on it.)
        //
        // At the beginning of each upkeep, if Tombstone Stairwell is on the battlefield, each player creates
        // a 2/2 black Zombie creature token with haste named Tombspawn for each creature card in their graveyard.
        //
        // At the beginning of each end step or when Tombstone Stairwell leaves the battlefield, destroy all tokens
        // created with Tombstone Stairwell. They can't be regenerated.
        addCard(Zone.BATTLEFIELD, playerA, "Tombstone Stairwell");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2);
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears", 3);

        // Both the cumulative upkeep and the triggered ability trigger at the beginning of upkeep, so you can choose
        // what order they resolve.
        // (2004-10-04)

        // turn 1 - pay upkeep and create tokens
        setChoice(playerA, "Cumulative upkeep"); // x2 triggers: create tokens + upkeep pay
        setChoice(playerA, "Yes"); // pay upkeep
        checkPermanentTapped("turn 1 - pays done", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp", true, 2);
        checkPermanentCount("turn 1 - tokens A", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tombspawn", 2);
        checkPermanentCount("turn 1 - tokens B", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Tombspawn", 3);

        // turn 1 - make sure tokens auto-destroyed
        waitStackResolved(1, PhaseStep.END_TURN);
        checkPermanentCount("turn 1 end - tokens A", 1, PhaseStep.END_TURN, playerA, "Tombspawn", 0);
        checkPermanentCount("turn 1 end - tokens B", 1, PhaseStep.END_TURN, playerB, "Tombspawn", 0);

        // turn 2 - keep create and destroy tokens on leave
        checkPermanentCount("turn 2 - tokens A", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Tombspawn", 2);
        checkPermanentCount("turn 2 - tokens B", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Tombspawn", 3);
        activateAbility(2, PhaseStep.BEGIN_COMBAT, playerA, "target destroy", "Tombstone Stairwell");
        waitStackResolved(2, PhaseStep.BEGIN_COMBAT);
        checkPermanentCount("turn 2 after destroy - tokens A", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tombspawn", 0);
        checkPermanentCount("turn 2 after destroy - tokens B", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Tombspawn", 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }
}
