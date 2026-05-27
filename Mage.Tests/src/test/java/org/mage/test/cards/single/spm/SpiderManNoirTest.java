package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SpiderManNoirTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Spider-Man Noir");

        attack(1, playerA, "Spider-Man Noir", playerB);
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Spider-Man Noir", 5, 5);
    }

}
