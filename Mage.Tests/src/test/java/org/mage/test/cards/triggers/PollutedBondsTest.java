package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PollutedBondsTest extends CardTestPlayerBase {

    final String pollutedBonds = "Polluted Bonds";

    @Test
    public void PollutedBondsSimple(){
        addCard(Zone.BATTLEFIELD, playerA, pollutedBonds);
        addCard(Zone.HAND, playerB, "Forest");
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Forest");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLife(playerA, 22);
        assertLife(playerB, 18);
    }

    @Test
    public void PollutedBondsOwnLand(){
        addCard(Zone.BATTLEFIELD, playerA, pollutedBonds);
        addCard(Zone.HAND, playerA, "Forest");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
