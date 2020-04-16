package org.mage.test.cards.single.iko;

import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ZilorthaStrengthIncarnateTest extends CardTestPlayerBase {

    @Test
    public void testNotPresent_damageResolvesLethalityAsNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Savai Sabertooth", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Drannith Healer", 1);

        attack(2, playerA, "Savai Sabertooth");
        block(2, playerB, "Drannith Healer", "Savai Sabertooth");

        execute();

        assertGraveyardCount(playerA, "Savai Sabertooth", 1);
        assertGraveyardCount(playerB, "Drannith Healer", 1);
    }

}
