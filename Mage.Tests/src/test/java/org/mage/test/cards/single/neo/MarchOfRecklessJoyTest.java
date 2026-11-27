package org.mage.test.cards.single.neo;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MarchOfRecklessJoyTest extends CardTestPlayerBase {

    /**
     * Up to two of the exiled cards. The plays are counted against the spell that gave the permission, which is in
     * the graveyard by the time they happen.
     */
    @Test
    public void testPlayUpToTwo() {
        // Exile the top X cards of your library. You may play up to two of those cards until the end of your next turn.
        addCard(Zone.HAND, playerA, "March of Reckless Joy"); // {X}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7); // one spare, so only the permission stops a third play
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 3);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of Reckless Joy", true);
        setChoice(playerA, "X=3");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("two plays used", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3 - 3);
        assertExileCount(playerA, "Lightning Bolt", 1);
    }
}
