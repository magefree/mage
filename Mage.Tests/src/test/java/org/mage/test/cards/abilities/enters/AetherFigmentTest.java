package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class AetherFigmentTest extends CardTestPlayerBase {

    /*
        Aether Figment   {1}{U}
        Creature - Illusion
        1/1
        Kicker {3} (You may pay an additional as you cast this spell.)
        Aether Figment can't be blocked.
        If Aether Figment was kicked, it enters the battlefield with two +1/+1 counters on it.
    */
    @Test
    public void testEnteringWithCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Aether Figment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Figment");
        setChoice(playerA, true); // use kicker

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Aether Figment", 1);
        assertPowerToughness(playerA, "Aether Figment", 3, 3);
    }
}
