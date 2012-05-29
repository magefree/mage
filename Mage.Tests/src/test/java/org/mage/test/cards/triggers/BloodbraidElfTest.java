package org.mage.test.cards.triggers;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx, Loki
 */
public class BloodbraidElfTest extends CardTestPlayerBase {
    @Test
    public void testCascade() {
        addCard(Constants.Zone.HAND, playerA, "Bloodbraid Elf");
        addCard(Constants.Zone.HAND, playerA, "Terminus");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Goblin Wardriver", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 6);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Terminus");

        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Bloodbraid Elf");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bloodbraid Elf", 1);
        assertPermanentCount(playerA, "Goblin Wardriver", 1);
    }
}
