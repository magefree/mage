package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CeaselessConflictTest extends CardTestPlayerBase {

    @Test
    public void testCreatesSpiritForEachNontokenCreatureYouControlledDestroyedThisWay() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Raise the Alarm");
        addCard(Zone.HAND, playerA, "Ceaseless Conflict");
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ceaseless Conflict");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Spirit Token", 2);
        assertPermanentCount(playerA, "Soldier Token", 0);
        assertPermanentCount(playerA, "Memnite", 0);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Ceaseless Conflict", 1);
    }
}
