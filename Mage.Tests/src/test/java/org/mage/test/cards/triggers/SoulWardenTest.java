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
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);        
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);        
        addCard(Zone.BATTLEFIELD, playerA, "Soul Warden", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.HAND, playerA, "Elite Vanguard", 2);
        addCard(Zone.HAND, playerA, "Raise the Alarm", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard"); // 2/1 creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm"); // put two 1/1 soldiers on the battlefield
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Lightning Bolt", "Soul Warden");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Elite Vanguard"); // should not gain life now that soul warden is dead

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Soul Warden", 1);
        assertPermanentCount(playerA, "Soldier Token", 2);
        assertPermanentCount(playerA, "Elite Vanguard", 2);
        assertLife(playerA, 23);
        assertLife(playerB, 20);
    }

}
