package org.mage.test.cards.single.zen;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class ChandraAblazeTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_PlusOneAbility_Manual() {
        // +1: Discard a card. If a red card is discarded this way, Chandra Ablaze deals 4 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Ablaze", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // activate +1 and kill lion
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        addTarget(playerA, "Silvercoat Lion"); // damage
        setChoice(playerA, "Lightning Bolt"); // discard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_PlusOneAbility_AI_ChooseBetterTarget() {
        // +1: Discard a card. If a red card is discarded this way, Chandra Ablaze deals 4 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Ablaze", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // AI must choose better action (kill lion)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    @Ignore // TODO: current choose logic uses first target, enable after new logic implemented (by simulations or improved choice)
    public void test_PlusOneAbility_AI_ChooseBetterDiscardCard() {
        // +1: Discard a card. If a red card is discarded this way, Chandra Ablaze deals 4 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Ablaze", 1);
        addCard(Zone.HAND, playerA, "Glittermonger", 10); // green
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // put after green, so AI must choose better, not first
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // AI must choose better discard card (bolt as red, not green)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_PlusTwoAbility_Manual() {
        // -2: Each player discards their hand, then draws three cards.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Ablaze", 1);
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears", 5);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        // activate -2 and discard all
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
        assertHandCount(playerB, 3);

        assertGraveyardCount(playerA, "Grizzly Bears", 5);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_PlusTwoAbility_AI_MustNotSeeOpponentHand_KeepBetterHand() {
        // AI must calc battlefield score by visible data, so it do not know opponent's hand,
        // but it is still able to cheat by look ahead in library (e.g. it "see" result of draw actions)

        // -2: Each player discards their hand, then draws three cards.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Ablaze", 1);
        //
        // TODO: it's another bug - AI can play land in some actions chain and can't play on another,
        //  comment and research AI logs (possible reason: AI don't wait stack resolve)
        addCard(Zone.HAND, playerA, "Mountain", 1);
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears", 5);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        // must not call any abilities
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // must keep better hand
        assertHandCount(playerA, "Grizzly Bears", 5);
        assertHandCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_PlusTwoAbility_AI_MustNotSeeOpponentHand_KeepWorseHand() {
        // -2: Each player discards their hand, then draws three cards.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Ablaze", 1);
        addCard(Zone.HAND, playerA, "Mountain", 1); // see prev test comments
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 5);

        // must not call any abilities
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // must keep worse hand
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerB, "Silvercoat Lion", 5);
    }
}
