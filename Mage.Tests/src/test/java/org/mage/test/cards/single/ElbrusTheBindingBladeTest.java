package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ElbrusTheBindingBladeTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Elbrus, the Binding Blade");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Air Elemental");
        attack(1, playerA, "Air Elemental");


        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15);
        assertPermanentCount(playerA, "Air Elemental", 1);
        assertPermanentCount(playerA, "Elbrus, the Binding Blade", 0);
        assertPermanentCount(playerA, "Withengar Unbound", 1);
    }

}
