package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FinishingBlowTest extends CardTestPlayerBase {

    @Test
    public void destroyCreature(){
        // Destroy target creature or planeswalker.
        addCard(Zone.HAND, playerA, "Finishing Blow");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Finishing Blow", "Grizzly Bears");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void destroyPlaneswalker(){
        // Destroy target creature or planeswalker.
        addCard(Zone.HAND, playerA, "Finishing Blow");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Basri Ket");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Finishing Blow", "Basri Ket");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertGraveyardCount(playerB, "Basri Ket", 1);
    }
}
