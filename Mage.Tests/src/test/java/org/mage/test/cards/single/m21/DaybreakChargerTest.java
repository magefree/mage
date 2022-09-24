package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DaybreakChargerTest extends CardTestPlayerBase {

    @Test
    public void etbTrigger(){
        // When Daybreak Charger enters the battlefield, target creature gets +2/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Daybreak Charger");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daybreak Charger");
        addTarget(playerA, "Daybreak Charger");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Daybreak Charger", 5, 1);
    }
}
