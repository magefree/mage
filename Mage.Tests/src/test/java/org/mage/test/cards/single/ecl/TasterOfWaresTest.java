package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TasterOfWaresTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.HAND, playerB, "Lightning Bolt", 3);
        addCard(Zone.HAND, playerA, "Taster of Wares");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Assailant", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Taster of Wares");
        addTarget(playerA, playerB);
        setChoice(playerB, "Lightning Bolt");
        setChoice(playerB, "Lightning Bolt");
        setChoice(playerB, "Lightning Bolt");
        setChoice(playerA, "Lightning Bolt");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
    }

}
