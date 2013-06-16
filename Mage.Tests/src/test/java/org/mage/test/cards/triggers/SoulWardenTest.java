package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 *
 *  Soul Warden:
 *    Whenever another creature enters the battlefield, you gain 1 life.
 */
public class SoulWardenTest extends CardTestPlayerBase {

    /**
     * Tests trigger won't happen after Soul Warden died
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Elite Vanguard", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Soul Warden", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Soul Warden");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }

}
