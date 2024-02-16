package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CanopyStalkerTest extends CardTestPlayerBase {

    @Test
    public void testMustBeBlocked(){
        // 4/2
        // Canopy Stalker must be blocked if able.
        // When Canopy Stalker dies, you gain 1 life for each creature that died this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Canopy Stalker");
        // 1/1 Flying
        addCard(Zone.BATTLEFIELD, playerB, "Scryb Sprites");
        attack(3, playerA, "Canopy Stalker");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerB, "Scryb Sprites", 1);

    }

    @Test
    public void testDiesEvent(){
        // 4/2
        // Canopy Stalker must be blocked if able.
        // When Canopy Stalker dies, you gain 1 life for each creature that died this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Canopy Stalker");
        // 2/2 Vanilla
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        attack(3, playerA, "Canopy Stalker");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 22);
    }
}
