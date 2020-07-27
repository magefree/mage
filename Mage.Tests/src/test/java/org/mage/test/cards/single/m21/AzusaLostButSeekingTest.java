package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AzusaLostButSeekingTest extends CardTestPlayerBase {

    private static final String azusa = "Azusa, Lost but Seeking";

    @Test
    @Ignore
    public void playAdditionalLands(){
        addCard(Zone.BATTLEFIELD, playerA, azusa);
        addCard(Zone.HAND, playerA, "Forest", 4);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Forest", 3);
    }
}
