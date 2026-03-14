package org.mage.test.cards.single.nph;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ArmWithAetherTest extends CardTestPlayerBase {

    // Until end of turn, creatures you control gain "Whenever this creature deals damage to an opponent, you may return target creature that player controls to its owner's hand."
    @Test
    public void testNoncombatDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Magmutt");
        addCard(Zone.HAND, playerA, "Arm with Aether");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arm with Aether");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:");
        addTarget(playerA, playerB);
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Balduvian Bears", 1);
    }
}
