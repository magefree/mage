package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */
public class RecoverTest extends CardTestPlayerBase {

    /**
     * 702.58a Recover is a triggered ability that functions only while the card
     * with recover is in a player's graveyard. “Recover [cost]” means “When a
     * creature is put into your graveyard from the battlefield, you may pay
     * [cost]. If you do, return this card from your graveyard to your hand.
     * Otherwise, exile this card.”
     */
    @Test
    public void test_Normal_ToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // You gain 4 life.
        // Recover {1}{W}
        addCard(Zone.HAND, playerA, "Sun's Bounty");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sun's Bounty");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerA, "Sun's Bounty", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 24);

        assertTappedCount("Plains", true, 4);

    }

    @Test
    public void test_Normal_ToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // You gain 4 life.
        // Recover {1}{W}
        addCard(Zone.HAND, playerA, "Sun's Bounty");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sun's Bounty");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Plains", true, 2);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertExileCount("Sun's Bounty", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 24);
    }

    @Test
    public void test_DieOther_Single_CanRecover() {
        addCustomEffect_TargetDestroy(playerA, 1);

        // Recover—Pay half your life, rounded up.
        addCard(Zone.GRAVEYARD, playerA, "Garza's Assassin");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy");
        addTarget(playerA, "Silvercoat Lion");
        setChoice(playerA, true); // pay half life

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Garza's Assassin", 1); // after recover
        assertLife(playerA, 20 / 2);
    }

    @Test
    public void test_DieOther_Multiple_CanRecover() {
        // ruling from wiki:
        // If multiple creatures are put into your graveyard from the battlefield at the same time, the recover
        // ability of a card already in your graveyard triggers that many times. Only the first one to resolve
        // will cause the card to move somewhere. By the time any of the other triggers resolve, the card won't be
        // in your graveyard anymore. You can still pay the recover cost, but nothing else will happen.

        addCustomEffect_TargetDestroy(playerA, 2);

        // Recover—Pay half your life, rounded up.
        addCard(Zone.GRAVEYARD, playerA, "Garza's Assassin");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        // raise 2 recover triggers, pay second trigger - it will be fizzled
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy");
        addTarget(playerA, "Silvercoat Lion^Grizzly Bears");
        setChoice(playerA, "Recover&mdash;Pay half your life"); // x2 triggers order
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkStackObject("on recover triggers", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Recover&mdash;Pay half your life", 2);
        setChoice(playerA, false); // first trigger resolve - do not pay and exile
        setChoice(playerA, true); // second trigger resolve - pay and fizzle

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Garza's Assassin", 1); // after first unpayed trigger
        assertLife(playerA, 20 / 2); // after second unpayed trigger
    }

    @Test
    public void test_DieItself_MustNotWork() {
        // ruling from wiki:
        // If a creature with recover is put into your graveyard from the battlefield, it doesn't cause its
        // own recover ability to trigger. Similarly, if another creature is put into your graveyard from
        // the battlefield at the same time that a card with recover is put there, it won't cause that
        // recover ability to trigger.

        addCustomEffect_TargetDestroy(playerA, 1);

        // Recover—Pay half your life, rounded up.
        addCard(Zone.BATTLEFIELD, playerA, "Garza's Assassin");

        // no recover
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy");
        addTarget(playerA, "Garza's Assassin");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Garza's Assassin", 1);
        assertLife(playerA, 20);
    }

    @Test
    public void test_DieItselfAndMultiple_MustNotWork() {
        // ruling from wiki:
        // If a creature with recover is put into your graveyard from the battlefield, it doesn't cause its
        // own recover ability to trigger. Similarly, if another creature is put into your graveyard from
        // the battlefield at the same time that a card with recover is put there, it won't cause that
        // recover ability to trigger.

        // reason: it's leaves-the-battlefield trigger and look back in time (source was on battlefield in that time, so no trigger)

        addCustomEffect_TargetDestroy(playerA, 2);

        // Recover—Pay half your life, rounded up.
        addCard(Zone.BATTLEFIELD, playerA, "Garza's Assassin");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // no recover (if you catch recover dialog then something wrong with isInUseableZone)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy");
        addTarget(playerA, "Garza's Assassin^Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Garza's Assassin", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertLife(playerA, 20);
    }
}
