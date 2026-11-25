package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class LivingLaserTest extends CardTestPlayerBase {

    @Test
    public void test_TokenForEachDiscardedCard() {
        // Haste
        // Whenever Living Laser attacks, for each card you've discarded this turn, create a token that's a copy of
        // Living Laser, except the token isn't legendary. The tokens enter tapped and attacking. Exile the tokens at
        // the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Living Laser"); // 4/4
        //
        // As an additional cost to cast this spell, discard a card. Draw two cards.
        addCard(Zone.HAND, playerA, "Tormenting Voice", 2); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 2); // card to discard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tormenting Voice");
        setChoice(playerA, "Grizzly Bears"); // discard cost
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tormenting Voice");
        setChoice(playerA, "Grizzly Bears"); // discard cost
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        attack(1, playerA, "Living Laser");
        checkPermanentTapped("tokens enter tapped", 1, PhaseStep.COMBAT_DAMAGE, playerA, "Living Laser", true, 1 + 2);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20 - 4 - 4 * 2);
        assertPermanentCount(playerA, "Living Laser", 1); // tokens exiled at the end step
    }

    @Test
    public void test_DiscardedOnOtherTurn() {
        // Whenever Living Laser attacks, for each card you've discarded this turn, create a token that's a copy of
        // Living Laser, except the token isn't legendary. The tokens enter tapped and attacking. Exile the tokens at
        // the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Living Laser"); // 4/4
        //
        // As an additional cost to cast this spell, discard a card. Draw two cards.
        addCard(Zone.HAND, playerA, "Tormenting Voice"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // card to discard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tormenting Voice");
        setChoice(playerA, "Grizzly Bears"); // discard cost

        attack(3, playerA, "Living Laser");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20 - 4);
        assertPermanentCount(playerA, "Living Laser", 1);
    }

    @Test
    public void test_DiscardedByOpponent() {
        // Whenever Living Laser attacks, for each card you've discarded this turn, create a token that's a copy of
        // Living Laser, except the token isn't legendary. The tokens enter tapped and attacking. Exile the tokens at
        // the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Living Laser"); // 4/4
        //
        // Target player discards two cards.
        addCard(Zone.HAND, playerA, "Mind Rot"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Grizzly Bears", 2); // cards to discard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Rot", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        attack(1, playerA, "Living Laser");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 2);
        assertLife(playerB, 20 - 4);
        assertPermanentCount(playerA, "Living Laser", 1);
    }
}
