package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AnointedChoristerTest extends CardTestPlayerBase {

    // {4}{W}: Anointed Chorister gets +3/+3 until end of turn.
    private final String chorister = "Anointed Chorister";


    @Test
    public void buff(){
        addCard(Zone.BATTLEFIELD, playerA, chorister);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{W}:");
        attack(3, playerA, chorister);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertLife(playerA, 24);
        assertLife(playerB, 16);
        assertPowerToughness(playerA, chorister, 1, 1);
    }
}
