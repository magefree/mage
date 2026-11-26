package org.mage.test.cards.single.m20;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Chandra, Acolyte of Flame: "0: Create two 1/1 red Elemental creature tokens.
 * They gain haste. Sacrifice them at the beginning of the next end step."
 * <p>
 * One instruction makes one delayed triggered ability (CR 603.7a) that
 * sacrifices both tokens, so the end step puts one trigger on the stack, not
 * one per token.
 */
public class ChandraAcolyteOfFlameTest extends CardTestPlayerBase {

    @Test
    public void testOneDelayedSacrificeForBothTokens() {
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Acolyte of Flame", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "0: Create two 1/1 red Elemental");
        checkPermanentCount("two tokens before the end step", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Elemental Token", 2);
        checkStackSize("one delayed trigger for both tokens", 1, PhaseStep.END_TURN, playerA, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Elemental Token", 0);
    }
}
