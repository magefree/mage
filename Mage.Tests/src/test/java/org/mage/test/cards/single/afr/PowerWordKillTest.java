package org.mage.test.cards.single.afr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PowerWordKillTest extends CardTestPlayerBase {

    // Destroy target non-Angel, non-Demon, non-Devil, non-Dragon creature.
    private final String powerWordKill = "Power Word Kill";

    @Test
    public void killGrizzlyBears(){
        addCard(Zone.HAND, playerA, powerWordKill);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, powerWordKill, "Grizzly Bears");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void canNotTargetChangeling(){
        addCard(Zone.HAND, playerA, powerWordKill);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Avian Changeling");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, powerWordKill, "Avian Changeling");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPermanentCount(playerB, "Avian Changeling", 1);
    }
}
