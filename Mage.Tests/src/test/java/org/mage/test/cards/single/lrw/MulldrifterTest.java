package org.mage.test.cards.single.lrw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class MulldrifterTest extends CardTestPlayerBase {

    /**
     * Reported bug: Muldrifter only draws 1 card. And only once during a turn if you haven't already drawn a card.
     * Example, If it is my turn and I play Muldrifter, no card is drawn for trigger.
     * <p>
     * If it is not my turn and I play Ghostly Flicker targeting Eternal Witness and Muldrifter, when Muldrifter enters play, only 1 card is drawn.
     * <p>
     * If I repeat the same thing in the same turn, the second time Muldrifter enters the battlefield, no cards are drawn.
     */
    @Test
    public void testMulldrifterNotEvoked() {
        setStrictChooseMode(true);

        // {4}{U} When Mulldrifter enters the battlefield, draw two cards.
        addCard(Zone.HAND, playerA, "Mulldrifter"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mulldrifter");
        setChoice(playerA, TestPlayer.CHOICE_NORMAL_COST); // cast regularly, not evoked
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mulldrifter", 1);
        assertHandCount(playerA, 2); // should have drawn 2 cards
    }

    /**
     *
     */
    @Test
    public void testMulldrifterEvoked() {
        setStrictChooseMode(true);

        // {4}{U} When Mulldrifter enters the battlefield, draw two cards. Evoke {2}{U}
        addCard(Zone.HAND, playerA, "Mulldrifter"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mulldrifter");
        setChoice(playerA, "Cast with Evoke alternative cost: {2}{U} (source: Mulldrifter");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setChoice(playerA, "When this permanent enters, if its evoke cost was paid, its controller sacrifices it"); // stack triggers

        execute();

        assertPermanentCount(playerA, "Mulldrifter", 0);
        assertGraveyardCount(playerA, "Mulldrifter", 1);
        assertHandCount(playerA, 2); // should have drawn 2 cards
    }

    /**
     *
     */
    @Test
    public void testMulldrifterFlickered() {
        setStrictChooseMode(true);

        // {4}{U} When Mulldrifter enters, draw two cards. Evoke {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Mulldrifter"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk Looter"); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // Ghostly Flicker {2}{U} Instant
        // Exile two target artifacts, creatures, and/or lands you control, then return those cards to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Ghostly Flicker");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ghostly Flicker");
        addTarget(playerA, "Mulldrifter^Merfolk Looter");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mulldrifter", 1);
        assertPermanentCount(playerA, "Merfolk Looter", 1);
        assertGraveyardCount(playerA, "Ghostly Flicker", 1);
        assertHandCount(playerA, 2); // should have drawn 2 cards
    }
}
