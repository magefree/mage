package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

public class DiscardCardCostTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Spell_AloneInHand_NotPlayable() {
        // As an additional cost to cast this spell, discard a card.
        // Draw two cards.
        addCard(Zone.HAND, playerA, "Tormenting Voice"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // the spell is on the stack when its costs are paid, so it can't discard itself
        checkPlayableAbility("not playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tormenting Voice", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Tormenting Voice", 1);
    }

    @Test
    public void test_Spell_WithOtherCard_DiscardsIt() {
        // As an additional cost to cast this spell, discard a card.
        // Draw two cards.
        addCard(Zone.HAND, playerA, "Tormenting Voice"); // {1}{R}
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tormenting Voice", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tormenting Voice");
        setChoice(playerA, "Grizzly Bears"); // discard cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Tormenting Voice", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_OrCost_AloneInHand_NotPlayable() {
        // As an additional cost to cast this spell, sacrifice a creature or discard a card.
        // Destroy target creature or planeswalker.
        addCard(Zone.HAND, playerA, "Bone Shards"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        checkPlayableAbility("not playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bone Shards", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_OrCost_WithOtherCard_DiscardsIt() {
        // As an additional cost to cast this spell, sacrifice a creature or discard a card.
        // Destroy target creature or planeswalker.
        addCard(Zone.HAND, playerA, "Bone Shards"); // {B}
        addCard(Zone.HAND, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bone Shards", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bone Shards", "Grizzly Bears");
        setChoice(playerA, "Island"); // discard cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Bone Shards", 1);
        assertGraveyardCount(playerA, "Island", 1);
    }

    @Test
    public void test_Spell_FromGraveyard_DiscardsLastCardInHand() {
        // Draw two cards.
        // Jump-start
        addCard(Zone.GRAVEYARD, playerA, "Chemister's Insight"); // {3}{U}
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Chemister's Insight with jump-start", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chemister's Insight with jump-start");
        setChoice(playerA, "Grizzly Bears"); // discard cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Chemister's Insight", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_ActivatedAbility_DiscardsLastCardInHand() {
        // Discard a card: Wild Mongrel gets +1/+1 and becomes the color of your choice until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Wild Mongrel"); // 2/2
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Grizzly Bears"); // discard cost
        setChoice(playerA, "Green");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Wild Mongrel", 3, 3);
    }

    @Test
    public void test_AI_DontCastAloneInHand() {
        // As an additional cost to cast this spell, discard a card.
        // Draw two cards.
        addCard(Zone.HAND, playerA, "Tormenting Voice"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Tormenting Voice", 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_AI_CastWithOtherCard() {
        // As an additional cost to cast this spell, discard a card.
        // Draw two cards.
        addCard(Zone.HAND, playerA, "Tormenting Voice"); // {1}{R}
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // can't be cast with Mountains
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Tormenting Voice", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
