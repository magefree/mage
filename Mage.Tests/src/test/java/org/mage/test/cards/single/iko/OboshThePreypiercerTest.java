package org.mage.test.cards.single.iko;


import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */

public class OboshThePreypiercerTest extends CardTestPlayerBase {

    @Test
    public void testZeroCMSIsHandledAsOdd() {
        setStrictChooseMode(true);
        // At the beginning of your upkeep, flip a coin. If you lose the flip, Mana Crypt deals 3 damage to you.
        // {T}: Add {C}{C}.
        addCard(Zone.BATTLEFIELD, playerA, "Mana Crypt");
        // Companion — Your starting deck contains only cards with odd converted mana costs and land cards.
        // If a source you control with an odd converted mana cost would deal damage to a permanent or player, it deals double that damage to that permanent or player instead.        
        addCard(Zone.BATTLEFIELD, playerA, "Obosh, the Preypiercer");

        // lose the flip
        setFlipCoinResult(playerA, false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 - 3);
    }
}