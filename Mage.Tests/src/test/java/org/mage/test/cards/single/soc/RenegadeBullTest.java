package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class RenegadeBullTest extends CardTestPlayerBase {

    @Test
    public void testInstantManaValueBoostAndAttackCopyFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Renegade Bull");
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        attack(1, playerA, "Renegade Bull", playerB);
        addTarget(playerA, "Lightning Bolt");
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Renegade Bull", 2, 5);
        assertExileCount("Lightning Bolt", 1);
        assertLife(playerB, 13);
    }
}
