package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx, Loki
 */
public class BloodbraidElfTest extends CardTestPlayerBase {
    @Test
    public void testCascade() {
        addCard(Zone.HAND, playerA, "Bloodbraid Elf");
        addCard(Zone.HAND, playerA, "Terminus");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Wardriver", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terminus");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bloodbraid Elf");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bloodbraid Elf", 1);
        assertPermanentCount(playerA, "Goblin Wardriver", 1);
    }
}
