package org.mage.test.cards.single.thb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */

public class WhirlwindDenialTest extends CardTestPlayerBase {

    private static final String denial = "Whirlwind Denial";
    private static final String guttersnipe = "Guttersnipe"; // trigger deals 2 damage on cast
    private static final String tithe = "Blood Tithe"; // deals 3 damage, gain 3 life

    private void baseWhirlwindTest(boolean payTrigger, boolean paySpell) {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3+4+4);
        addCard(Zone.BATTLEFIELD, playerA, guttersnipe);
        addCard(Zone.HAND, playerA, tithe); // Player A tries to cast Blood Tithe for 3 damage + 2 damage
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, denial); // Player B denies it

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tithe);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, denial);
        setChoice(playerA, payTrigger);
        setChoice(playerA, paySpell);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - (payTrigger ? 2 : 0) - (paySpell ? 3 : 0));
    }

    @Test
    public void testWhirlwindPayAllCosts() {
        baseWhirlwindTest(true, true);
    }

    @Test
    public void testWhirlwindPayTrigger() {
        baseWhirlwindTest(true, false);
    }

    @Test
    public void testWhirlwindPaySpell() {
        baseWhirlwindTest(false, true);
    }

    @Test
    public void testWhirlwindPayNone() {
        baseWhirlwindTest(false, false);
    }

}
