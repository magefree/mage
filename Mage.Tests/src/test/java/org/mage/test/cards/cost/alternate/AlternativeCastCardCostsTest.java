package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 118.9d
 * If an alternative cost is being paid to cast a spell, any additional costs, cost increases, and cost reductions that
 * affect that spell are applied to that alternative cost. (See rule 601.2f.)
 * <p>
 * 601.2f
 * The player determines the total cost of the spell. Usually this is just the mana cost. Some spells have additional or
 * alternative costs. Some effects may increase or reduce the cost to pay, or may provide other alternative costs. Costs
 * may include paying mana, tapping permanents, sacrificing permanents, discarding cards, and so on. The total cost is
 * the mana cost or alternative cost (as determined in rule 601.2b), plus all additional costs and cost increases, and
 * minus all cost reductions.
 *
 * @author DarioBarrabinoDev
 */
public class AlternativeCastCardCostsTest extends CardTestPlayerBase {

    @Test
    public void test_Flashback_CardCostIncrease_Playable() {
        // As long as it's not your turn, this spell costs {3} more to cast.
        // Return all nonland permanents to their owner's hands. End the turn.
        addCard(Zone.GRAVEYARD, playerA, "Hurkyl's Final Meditation"); // {4}{U}{U}{U}
        //
        // Flash
        // When Snapcaster Mage enters, target instant or sorcery card in your graveyard gains flashback until end of turn.
        // The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 9); // 2 for Snapcaster Mage, 9 for flashback

        // turn 2 is playerB's turn, so Hurkyl's Final Meditation costs {3} more
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Hurkyl's Final Meditation"); // gains flashback
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        // {4}{U}{U}{U} + {3}, only 9 lands left
        checkPlayableAbility("can't pay the increase", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Flashback_CardCostIncrease_Cast() {
        // As long as it's not your turn, this spell costs {3} more to cast.
        // Return all nonland permanents to their owner's hands. End the turn.
        addCard(Zone.GRAVEYARD, playerA, "Hurkyl's Final Meditation"); // {4}{U}{U}{U}
        //
        // Flash
        // When Snapcaster Mage enters, target instant or sorcery card in your graveyard gains flashback until end of turn.
        // The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 10); // 2 for Snapcaster Mage, 10 for flashback

        // turn 2 is playerB's turn, so Hurkyl's Final Meditation costs {3} more
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Hurkyl's Final Meditation"); // gains flashback
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        checkPlayableAbility("can pay the increase", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback", true);
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        // it ends the turn on resolve, so check the payment before it
        checkPermanentTapped("paid", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Island", true, 2 + 10);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, "Hurkyl's Final Meditation", 1); // flashback exiles it
        assertHandCount(playerA, "Snapcaster Mage", 1); // returned by Hurkyl's Final Meditation
    }

    @Test
    public void test_Harmonize_CardCostIncrease_Cast() {
        // As long as it's not your turn, this spell costs {3} more to cast.
        // Return all nonland permanents to their owner's hands. End the turn.
        addCard(Zone.GRAVEYARD, playerA, "Hurkyl's Final Meditation"); // {4}{U}{U}{U}
        //
        // Flash
        // When this creature enters, target instant or sorcery card in your graveyard gains harmonize until end of turn.
        // Its harmonize cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Songcrafter Mage"); // {G}{U}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1 + 10); // 1 for Songcrafter Mage, 10 for harmonize

        // turn 2 is playerB's turn, so Hurkyl's Final Meditation costs {3} more
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Songcrafter Mage");
        addTarget(playerA, "Hurkyl's Final Meditation"); // gains harmonize
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Harmonize");
        setChoice(playerA, false); // don't tap a creature to reduce the cost
        // it ends the turn on resolve, so check the payment before it
        checkPermanentTapped("paid", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Island", true, 1 + 10);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, "Hurkyl's Final Meditation", 1); // harmonize exiles it
    }

    @Test
    public void test_Foretell_CardCostIncrease_Playable() {
        removeAllCardsFromHand(playerA);

        // As long as it's not your turn, this spell costs {3} more to cast.
        // Return all nonland permanents to their owner's hands. End the turn.
        addCard(Zone.HAND, playerA, "Hurkyl's Final Meditation"); // {4}{U}{U}{U}
        //
        // Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost
        // reduced by {2}.
        // Whenever you foretell a card, Dream Devourer gets +2/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 7); // 2 to foretell, 7 left

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("foretold", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hurkyl's Final Meditation", 1);

        // turn 2 is playerB's turn: {2}{U}{U}{U} + {3}, only 7 lands left
        checkPlayableAbility("can't pay the increase", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {2}{U}{U}{U}", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Foretell_CardCostIncrease_Cast() {
        removeAllCardsFromHand(playerA);

        // As long as it's not your turn, this spell costs {3} more to cast.
        // Return all nonland permanents to their owner's hands. End the turn.
        addCard(Zone.HAND, playerA, "Hurkyl's Final Meditation"); // {4}{U}{U}{U}
        //
        // Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost
        // reduced by {2}.
        // Whenever you foretell a card, Dream Devourer gets +2/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 8); // 2 to foretell, 8 left

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("foretold", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hurkyl's Final Meditation", 1);

        // turn 2 is playerB's turn: {2}{U}{U}{U} + {3}
        checkPlayableAbility("can pay the increase", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {2}{U}{U}{U}", true);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {2}{U}{U}{U}");
        // it ends the turn on resolve, so check the payment before it
        checkPermanentTapped("paid", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", true, 2 + 8);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, "Hurkyl's Final Meditation", 1); // end the turn exiles it from the stack
    }

    @Test
    public void test_Flashback_CardPrepareCost_Cast() {
        // As an additional cost to cast Skeletal Scrying, exile X cards from your graveyard.
        // You draw X cards and you lose X life.
        addCard(Zone.GRAVEYARD, playerA, "Skeletal Scrying"); // {X}{B}
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2); // to exile
        //
        // Flash
        // When Snapcaster Mage enters, target instant or sorcery card in your graveyard gains flashback until end of turn.
        // The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Skeletal Scrying"); // gains flashback
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerA, "X=2");
        setChoice(playerA, "Grizzly Bears", 2); // exile cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Skeletal Scrying", 1); // flashback exiles it
        assertExileCount(playerA, "Grizzly Bears", 2); // paid the card's own additional cost
        assertLife(playerA, 20 - 2);
    }
}
