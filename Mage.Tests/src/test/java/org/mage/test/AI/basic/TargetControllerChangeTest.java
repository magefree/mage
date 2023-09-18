package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TargetControllerChangeTest extends CardTestPlayerBase {

    @Test
    public void test_OpponentMakeChooseInsteadPlayer_User() {
        // Gain control of target creature of an opponent’s choice they control.
        addCard(Zone.HAND, playerA, "Evangelize", 1); // {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Evangelize"); // do not call direct target setup
        addTarget(playerA, playerB); // choose target opponent
        setChoice(playerA, false); // no buyback
        //
        addTarget(playerB, "Balduvian Bears"); // give small bear to A

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Evangelize", 1);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_OpponentMakeChooseInsteadPlayer_AI() {
        // Gain control of target creature of an opponent’s choice they control.
        addCard(Zone.HAND, playerA, "Evangelize", 1); // {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Evangelize"); // do not call direct target setup
        //addTarget(playerA, playerB); // choose target opponent - AI must choose itself
        //setChoice(playerA, false); // no buyback - AI must choose itself
        //
        //addTarget(playerB, "Balduvian Bears"); // give small bear to A - AI must choose itself

        //setStrictChooseMode(true); // AI must choose
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Evangelize", 1);
        assertPermanentCount(playerA, "Balduvian Bears", 1); // AI give smallest permanent to A as bad effect for target (target control change)
    }
}
