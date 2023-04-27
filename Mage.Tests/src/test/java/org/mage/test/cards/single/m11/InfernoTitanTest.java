package org.mage.test.cards.single.m11;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class InfernoTitanTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_MustAbleToTargetPlaneswalkers() {
        // bug: https://github.com/magefree/mage/issues/7276

        // Whenever Inferno Titan enters the battlefield or attacks, it deals 3 damage divided as you choose among one, two, or three targets.
        addCard(Zone.HAND, playerA, "Inferno Titan"); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Acolyte of Flame", 1); // 4
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        // cast and divide damage (2x to creature and 1x to planeswalker)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inferno Titan");
        addTargetAmount(playerA, "Grizzly Bears", 2);
        addTargetAmount(playerA, "Chandra, Acolyte of Flame", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Inferno Titan", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Chandra, Acolyte of Flame", CounterType.LOYALTY, 4 - 1);
    }

    @Test
    public void test_AI_MustPlayWithoutFreeze() {
        // bug: game freeze on AI usage
        // https://github.com/magefree/mage/issues/6330

        // Whenever Inferno Titan enters the battlefield or attacks, it deals 3 damage divided as you choose among one, two, or three targets.
        addCard(Zone.HAND, playerA, "Inferno Titan"); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        // AI must play inferno and kill opponent's creature and make 1 damage to player
        // cast and divide damage (2x to creature and 1x to player)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Inferno Titan", 1); // own in safe
        assertPermanentCount(playerA, "Grizzly Bears", 1); // own in safe
        assertGraveyardCount(playerB, "Grizzly Bears", 1); // 2x damage - kill
        assertLife(playerB, 20 - 1); // 1x damage
    }

    @Test
    public void test_AI_MustAttackPlaneswalkerInsteadPlayer() {
        // bug: game freeze on AI usage
        // https://github.com/magefree/mage/issues/6330

        // Whenever Inferno Titan enters the battlefield or attacks, it deals 3 damage divided as you choose among one, two, or three targets.
        addCard(Zone.HAND, playerA, "Inferno Titan"); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Chandra, Acolyte of Flame", 1); // 4

        // AI must play inferno and kill opponent's creature and make 1 damage to planeswalker instead player
        // cast and divide damage (2x to creature and 1x to planeswalker)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Inferno Titan", 1); // own in safe
        assertPermanentCount(playerA, "Grizzly Bears", 1); // own in safe
        assertGraveyardCount(playerB, "Grizzly Bears", 1); // 2x damage - kill
        assertCounterCount(playerB, "Chandra, Acolyte of Flame", CounterType.LOYALTY, 4 - 1); // 1x damage
    }
}
