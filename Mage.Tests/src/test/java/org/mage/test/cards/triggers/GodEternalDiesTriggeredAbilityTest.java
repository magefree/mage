package org.mage.test.cards.triggers;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Alex-Vasile
 */
public class GodEternalDiesTriggeredAbilityTest extends CardTestPlayerBase {

    // When God-Eternal Bontu dies or is put into exile from the battlefield,
    // you may put it into its owner’s library third from the top.
    private static final String godEternalBontu = "God-Eternal Bontu";

    /**
     * Test that the ability triggers for dying.
     */
    @Test
    public void dyingTriggers() {
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, godEternalBontu);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", godEternalBontu);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, godEternalBontu, 0);
        assertGraveyardCount(playerA, godEternalBontu, 0);
        Card card = currentGame.getCard(playerA.getLibrary().getCardList().get(2));
        Assert.assertEquals(card.getName(), godEternalBontu);
    }

    /**
     * Test that the ability triggers for exiling.
     */
    @Test
    public void exilingTriggers() {
        addCard(Zone.HAND, playerA, "Anguished Unmaking");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, godEternalBontu);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anguished Unmaking", godEternalBontu);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, godEternalBontu, 0);
        assertExileCount(playerA, godEternalBontu, 0);
        Card card = currentGame.getCard(playerA.getLibrary().getCardList().get(2));
        Assert.assertEquals(card.getName(), godEternalBontu);
    }

    /**
     * Test that the ability does not trigger for bouncing
     */
    @Test
    public void bounceDoesNotTrigger() {
        // {1}{U}
        // Return target creature to its owner’s hand.
        // You may have Shapeshifters you control become copies of that creature until end of turn.
        addCard(Zone.HAND, playerA, "Absorb Identity");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, godEternalBontu);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Absorb Identity", godEternalBontu);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, godEternalBontu, 0);
        assertHandCount(playerA, godEternalBontu, 1);
        assertLibraryCount(playerA, godEternalBontu, 0);
    }
}
