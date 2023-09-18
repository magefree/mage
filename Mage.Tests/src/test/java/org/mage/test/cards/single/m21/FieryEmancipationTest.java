package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FieryEmancipationTest extends CardTestPlayerBase {

    @Test
    public void directDamage() {
        // If a source you control would deal damage to a permanent or player, it deals triple that damage to that permanent or player instead.
        addCard(Zone.BATTLEFIELD, playerA, "Fiery Emancipation");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLife(playerB, 14);
    }

    @Test
    public void directDamagePlaneswalker() {
        // If a source you control would deal damage to a permanent or player, it deals triple that damage to that permanent or player instead.
        addCard(Zone.BATTLEFIELD, playerA, "Fiery Emancipation");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Basri Ket");
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Basri Ket");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertGraveyardCount(playerB, "Basri Ket", 1);
    }
}
