package org.mage.test.cards.single.bfz;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by goesta on 11/02/2017.
 */
public class KalastriaHealerTest extends CardTestPlayerBase {

    @Test
    public void testThatAllyTokenTriggersEffekt() {
        addCard(Zone.BATTLEFIELD, playerA, "Kalastria Healer");
        addCard(Zone.BATTLEFIELD, playerA, "Captain's Claws");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Kalastria Healer");
        attack(1, playerA, "Kalastria Healer");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 16);
    }
}
