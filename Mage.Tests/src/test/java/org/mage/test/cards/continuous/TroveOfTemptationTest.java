package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TroveOfTemptationTest extends CardTestPlayerBase {

    @Test
    @Ignore // TODO: 2019-04-28 - improve and uncomment test after computer player can process playerMustBeAttackedIfAble restriction
    public void test_SingleOpponentMustAttack() {
        // Each opponent must attack you or a planeswalker you control with at least one creature each combat if able.
        addCard(Zone.BATTLEFIELD, playerA, "Trove of Temptation");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 1); // 2/2

        setStopAt(2, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
    }
}
