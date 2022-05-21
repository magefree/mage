package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AzusaLostButSeekingTest extends CardTestPlayerBase {

    private static final String azusa = "Azusa, Lost but Seeking";

    @Test
    public void playAdditionalLands() {
        addCard(Zone.BATTLEFIELD, playerA, azusa);
        addCard(Zone.HAND, playerA, "Forest", 4);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        checkPlayableAbility("4th land not possible", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Forest", false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }
}
