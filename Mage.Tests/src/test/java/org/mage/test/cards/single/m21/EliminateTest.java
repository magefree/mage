package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EliminateTest extends CardTestPlayerBase {

    @Test
    public void destroyCreature(){
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Crab");
        addCard(Zone.HAND, playerA, "Eliminate");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eliminate", "Ancient Crab");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertGraveyardCount(playerB, "Ancient Crab", 1);
    }

    @Test
    public void destroyPlaneswalker(){
        addCard(Zone.BATTLEFIELD, playerB, "Basri Ket");
        addCard(Zone.HAND, playerA, "Eliminate");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eliminate", "Basri Ket");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertGraveyardCount(playerB, "Basri Ket", 1);
    }
}
