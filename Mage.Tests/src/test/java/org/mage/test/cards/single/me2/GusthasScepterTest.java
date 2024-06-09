package org.mage.test.cards.single.me2;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class GusthasScepterTest extends CardTestPlayerBase {
    
    private static final String SCEPTER = "Gustha's Scepter";
    private static final String OFFERING = "Harmless Offering";
    private static final String LION = "Silvercoat Lion";

    /**
     * : Exile a card from your hand face down. You may look at it for as long as it remains exiled.
     * {T}: Return a card you own exiled with Gustha’s Scepter to your hand.
     * When you lose control of Gustha’s Scepter, put all cards exiled with Gustha’s Scepter into their owner’s graveyard.
     */
    @Test
    public void testEffect() {
        addCard(Zone.HAND, playerA, LION);
        addCard(Zone.BATTLEFIELD, playerA, SCEPTER);

        // Player A activates scepter turn 1, exiling Lion
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Exile");
        addTarget(playerA, LION);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Return");
        setChoice(playerA, LION);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(activePlayer, LION, 1);
    }

    @Test
    public void testLostControl() {
        addCard(Zone.HAND, playerA, LION);
        addCard(Zone.HAND, playerA, OFFERING);
        addCard(Zone.BATTLEFIELD, playerA, SCEPTER);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // Player A activates scepter turn 1, exiling Lion
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Exile");
        addTarget(playerA, LION);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Player A casts Harmless Offering, giving control of Scepter to Player B and triggering its effect
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, OFFERING);
        addTarget(playerA, playerB);
        addTarget(playerA, SCEPTER);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, LION, 1);
    }

}
