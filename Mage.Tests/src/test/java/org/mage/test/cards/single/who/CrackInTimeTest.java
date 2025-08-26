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
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // exile on etb
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crack in Time");
        addTarget(playerA, "Balduvian Bears"); // exile on etb
        checkExileCount("on etb Balduvian", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkExileCount("on etb Grizzly", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 0);

        // exile on next precombat main
        addTarget(playerA, "Grizzly Bears"); // exile on etb
        checkExileCount("on main Balduvian", 3, PhaseStep.BEGIN_COMBAT, playerB, "Balduvian Bears", 1);
        checkExileCount("on main Grizzly", 3, PhaseStep.BEGIN_COMBAT, playerB, "Grizzly Bears", 1);

        // destroy and return
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Crack in Time");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Balduvian Bears", 0);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertExileCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }
}
