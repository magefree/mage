package org.mage.test.cards.single.clb;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class DynaheirInvokerAdeptTest extends CardTestPlayerBase{
    
    private static final String DYNA = "Dynaheir, Invoker Adept";
    private static final String BUTCHER = "Battlefield Butcher";
    private static final String ACOLYTE = "Acolyte of Xathrid";

    /**
    * Haste
    * You may activate abilities of other creatures you control as though those creatures had haste.
    * {T}: When you next activate an ability that isn’t a mana ability this turn by spending four or more mana to activate it, copy that ability. You may choose new targets for the copy.
     */
    @Test
    public void testEffect() {

        addCard(Zone.BATTLEFIELD, playerA, DYNA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 15);
        addCard(Zone.HAND, playerA, ACOLYTE);
        addCard(Zone.HAND, playerA, BUTCHER);

        // Activate Dyna
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: When");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Cast Acolyte, should be able to activate, should not copy.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ACOLYTE, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Cast Butcher, should be able to activate, should copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, BUTCHER, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        // Opponent should be at 15 life.
        assertLife(playerB, 15);
    }
}
