package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CrackInTimeTest extends CardTestPlayerBase {

    @Test
    public void test_ExileUntilLeaves() {
        addCustomEffect_TargetDestroy(playerA);

        // When Crack in Time enters the battlefield and at the beginning of your precombat main phase,
        // exile target creature an opponent controls until Crack in Time leaves the battlefield.
        addCard(Zone.HAND, playerA, "Crack in Time"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crack in Time");
        addTarget(playerA, "Balduvian Bears"); // exile on etb
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("on exile", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Balduvian Bears", 1);

        // destroy and return
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Crack in Time");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkExileCount("on return", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 0);
        checkPermanentCount("on return", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
