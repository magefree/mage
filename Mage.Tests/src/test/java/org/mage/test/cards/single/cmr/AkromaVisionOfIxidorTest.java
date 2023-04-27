package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class AkromaVisionOfIxidorTest extends CardTestPlayerBase {

    @Test
    public void test_MustBoostCreatures() {
        // https://github.com/magefree/mage/issues/7209

        // At the beginning of each combat, until end of turn, each other creature you control gets +1/+1 if it has flying, +1/+1 if it has first strike, and so on
        addCard(Zone.BATTLEFIELD, playerA, "Akroma, Vision of Ixidor", 1);
        //
        // Flying
        addCard(Zone.BATTLEFIELD, playerA, "Shalai, Voice of Plenty", 1); // 3/4
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        checkPT("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Vision of Ixidor", 6, 6);
        checkPT("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shalai, Voice of Plenty", 3, 4);
        checkPT("before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Balduvian Bears", 2, 2);

        // on combat it must get boost for flying
        waitStackResolved(1, PhaseStep.BEGIN_COMBAT);
        checkPT("no change for itself", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Vision of Ixidor", 6, 6);
        checkPT("boost fly", 1, PhaseStep.BEGIN_COMBAT, playerA, "Shalai, Voice of Plenty", 3 + 1, 4 + 1);
        checkPT("no change for opponent", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Balduvian Bears", 2, 2);

        // end boost
        checkPT("end boost on end turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Shalai, Voice of Plenty", 3, 4);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }
}
