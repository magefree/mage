package org.mage.test.cards.single.m11;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class InfernoTitanTest extends CardTestPlayerBase {

    @Test
    public void test_MustAbleToTargetPlaneswalkers() {
        // bug: https://github.com/magefree/mage/issues/7276

        // Whenever Inferno Titan enters the battlefield or attacks, it deals 3 damage divided as you choose among one, two, or three targets.
        addCard(Zone.HAND, playerA, "Inferno Titan"); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Acolyte of Flame", 1); // 4
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        // cast and devide damage (2x to creature and 1x to planeswalker)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inferno Titan");
        addTargetAmount(playerA, "Grizzly Bears", 2);
        addTargetAmount(playerA, "Chandra, Acolyte of Flame", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Inferno Titan", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Chandra, Acolyte of Flame", CounterType.LOYALTY, 4 - 1);
    }
}
