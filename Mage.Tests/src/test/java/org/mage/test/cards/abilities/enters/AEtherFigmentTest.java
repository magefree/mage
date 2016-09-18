package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class AEtherFigmentTest extends CardTestPlayerBase {

    /*
        AEther Figment   {1}{U}
        Creature - Illusion
        1/1
        Kicker {3} (You may pay an additional as you cast this spell.)
        AEther Figment can't be blocked.
        If AEther Figment was kicked, it enters the battlefield with two +1/+1 counters on it.
    */
    @Test
    public void testEnteringWithCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "AEther Figment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "AEther Figment");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "AEther Figment", 1);
        assertPowerToughness(playerA,  "AEther Figment", 3, 3);
    }
}
