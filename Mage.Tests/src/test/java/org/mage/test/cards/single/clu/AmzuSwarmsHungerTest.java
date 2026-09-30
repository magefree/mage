package org.mage.test.cards.single.clu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AmzuSwarmsHungerTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Amzu, Swarm's Hunger");
        addCard(Zone.GRAVEYARD, playerA, "Esper Sentinel");
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerA, "Raise the Past");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Past");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Insect Token", 3, 3);
    }
}
