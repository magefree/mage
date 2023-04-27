package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BonePitBruteTest extends CardTestPlayerBase {

    @Test
    public void boostETB(){
        // {4}{R}{R}
        // When Bone Pit Brute enters the battlefield, target creature gets +4/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Bone Pit Brute");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bone Pit Brute");
        addTarget(playerA, "Bone Pit Brute");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Bone Pit Brute", 8, 5);
    }
}
