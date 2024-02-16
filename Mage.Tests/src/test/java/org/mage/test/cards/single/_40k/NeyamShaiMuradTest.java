package org.mage.test.cards.single._40k;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class NeyamShaiMuradTest extends CardTestPlayerBase {

    private static final String NEYAM = "Neyam Shai Murad";
    private static final String LION = "Silvercoat Lion";

    // Rogue Trader -- Whenever Neyam Shai Murad deals combat damage to a player, you may have that player return target permanent card from their graveyard to their hand.
    // If you do, that player chooses a permanent card in your graveyard, then you put it onto the battlefield under your control.
    @Test
    public void testEffect() {
        addCard(Zone.BATTLEFIELD, playerA, NEYAM);
        addCard(Zone.GRAVEYARD, playerA, LION);
        addCard(Zone.GRAVEYARD, playerB, LION);

        // Attack with Neyam, effect triggers
        attack(1, playerA, NEYAM);
        setChoice(playerA, true);
        addTarget(playerA, LION);
        setChoice(playerB, LION);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Player A should have a lion on board, player B should have a lion in hand
        assertPermanentCount(playerA, LION, 1);
        assertHandCount(playerB, 1);   
    }
    
}
