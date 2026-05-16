package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class StensianSanguinistTest extends CardTestPlayerBase {

    @Test
    public void testCombatDamageFromChosenCreaturePreparesExsanguinate() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Stensian Sanguinist");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.LIBRARY, playerA, "Island", 3);
        addCard(Zone.LIBRARY, playerB, "Island", 3);

        attack(1, playerA, "Grizzly Bears", playerB);
        addTarget(playerA, "Grizzly Bears");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Exsanguinate");
        setChoice(playerA, "X=2");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 16);
        assertGraveyardCount(playerA, "Exsanguinate", 0);
    }
}
