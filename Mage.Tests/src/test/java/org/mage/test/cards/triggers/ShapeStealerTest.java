package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ShapeStealerTest extends CardTestPlayerBase {

    private String shapeStealer = "Shape Stealer";
    private String myojinOfCleansingFire = "Myojin of Cleansing Fire";

    @Test
    public void testShapeStealerSingleBlocker() {
        addCard(Zone.BATTLEFIELD, playerA, shapeStealer);
        addCard(Zone.BATTLEFIELD, playerB, myojinOfCleansingFire);
        attack(1, playerA, shapeStealer);
        block(1, playerB, myojinOfCleansingFire, shapeStealer);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        assertDamageReceived(playerA, shapeStealer, 4);
        assertPowerToughness(playerA, shapeStealer, 4, 6);
        assertDamageReceived(playerB, myojinOfCleansingFire, 4);
    }
}
