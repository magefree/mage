package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BurlfistOakTest extends CardTestPlayerBase {


    @Test
    public void boostOnCardDraw(){
        // {2}{G}{G}
        // Whenever you draw a card, Burlfist Oak gets +2/+2 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Burlfist Oak");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Burlfist Oak", 4, 5);
    }
}
