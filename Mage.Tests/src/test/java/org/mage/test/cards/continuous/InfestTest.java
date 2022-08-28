package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Infest
 *    All creatures get -2/-2 until end of turn.
 *
 * @author noxx
 */
public class InfestTest extends CardTestPlayerBase {

    @Test
    public void testMassBoostEffectLocked() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 2);

        addCard(Zone.HAND, playerA, "Infest");
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Infest", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertGraveyardCount(playerA, "Elite Vanguard", 2);
        assertPermanentCount(playerA, "Grizzly Bears", 2);
    }
}
