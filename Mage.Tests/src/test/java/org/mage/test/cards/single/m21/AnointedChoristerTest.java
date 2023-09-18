package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AnointedChoristerTest extends CardTestPlayerBase {


    private final String chorister = "Anointed Chorister";


    @Test
    public void buff(){
        // 1/1 Lifelink
        addCard(Zone.BATTLEFIELD, playerA, chorister);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);

        // {4}{W}: Anointed Chorister gets +3/+3 until end of turn.
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{W}:");
        attack(3, playerA, chorister);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 24);
        assertLife(playerB, 16);
        assertPowerToughness(playerA, chorister, 1, 1);
    }
}
