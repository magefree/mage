package org.mage.test.cards.single.tmt;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MadameNullPowerBrokerTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Madame Null, Power Broker");
        addCard(Zone.HAND, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 4, 4);
        assertLife(playerA, 18);
    }
}
