package org.mage.test.cards.single.ody;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MillikinTest extends CardTestPlayerBase {

    @Test
    public void testNonManaAbilityMana() {
        // Since CR 20260807, Millikin's ability is not a mana ability
        // Ensure that it can be stifled
        // https://github.com/magefree/mage/pull/16054

        addCard(Zone.BATTLEFIELD, playerA, "Millikin");
        addCard(Zone.HAND, playerB, "Stifle");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Stifle");
        addTarget(playerB, "stack ability");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertManaPool(playerA, ManaType.COLORLESS, 0);
        assertGraveyardCount(playerA, 1);
    }
}
