package org.mage.test.cards.single.msc;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ImmortusMasterOfEternityTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling");
        addCard(Zone.BATTLEFIELD, playerA, "Immortus, Master of Eternity");
        addCard(Zone.HAND, playerA, "Echo of Eons");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.LIBRARY, playerA, "Fugitive Wizard");
        addCard(Zone.LIBRARY, playerA, "Reach Through Mists");
        addCard(Zone.LIBRARY, playerA, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echo of Eons");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertManaPool(playerA, ManaType.BLUE, 7);
        checkPlayableAbility("Should be able to cast noncreature spells", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Reach Through Mists", true);
        checkPlayableAbility("Should not be able to cast creature spells", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Fugitive Wizard", false);
    }

}
