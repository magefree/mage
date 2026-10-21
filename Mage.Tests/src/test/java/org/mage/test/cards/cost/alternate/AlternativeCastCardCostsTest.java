package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlternativeCastCardCostsTest extends CardTestPlayerBase {

    private static final String hurkyl = "Hurkyl's Final Meditation";
    private static final String snapcaster = "Snapcaster Mage";

    private void prepareFlashbackHurkyl(int islands) {
        // As long as it's not your turn, this spell costs {3} more to cast.
        // Return all nonland permanents to their owner's hands. End the turn.
        addCard(Zone.GRAVEYARD, playerA, hurkyl); // {4}{U}{U}{U}
        //
        // Flash
        // When Snapcaster Mage enters, target instant or sorcery card in your graveyard gains flashback until end of turn.
        addCard(Zone.HAND, playerA, snapcaster); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", islands);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, snapcaster);
        addTarget(playerA, hurkyl);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
    }

    @Test
    public void test_Flashback_CardCostIncrease_Playable() {
        prepareFlashbackHurkyl(2 + 9);

        // {4}{U}{U}{U} + {3}, only 9 lands left
        checkPlayableAbility("can't pay the increase", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Flashback_CardCostIncrease_Cast() {
        prepareFlashbackHurkyl(2 + 10);

        checkPlayableAbility("can pay the increase", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback", true);
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        // it ends the turn on resolve, so check the payment before it
        checkPermanentTapped("paid", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Island", true, 2 + 10);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, hurkyl, 1);
        assertHandCount(playerA, snapcaster, 1);
    }

    @Test
    public void test_Harmonize_CardCostIncrease_Cast() {
        addCard(Zone.GRAVEYARD, playerA, hurkyl); // {4}{U}{U}{U}
        //
        // Flash
        // When this creature enters, target instant or sorcery card in your graveyard gains harmonize until end of turn.
        // Its harmonize cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Songcrafter Mage"); // {G}{U}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1 + 10);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Songcrafter Mage");
        addTarget(playerA, hurkyl);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Harmonize");
        setChoice(playerA, false); // don't tap a creature
        checkPermanentTapped("paid", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Island", true, 1 + 10);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, hurkyl, 1);
    }

    private void prepareForetoldHurkyl(int islands) {
        removeAllCardsFromHand(playerA);

        // Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer");
        addCard(Zone.HAND, playerA, hurkyl); // {4}{U}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + islands);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("foretold", 1, PhaseStep.PRECOMBAT_MAIN, playerA, hurkyl, 1);
    }

    @Test
    public void test_Foretell_CardCostIncrease_Playable() {
        prepareForetoldHurkyl(7);

        // {2}{U}{U}{U} + {3}, only 7 lands left
        checkPlayableAbility("can't pay the increase", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {2}{U}{U}{U}", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Foretell_CardCostIncrease_Cast() {
        prepareForetoldHurkyl(8);

        checkPlayableAbility("can pay the increase", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {2}{U}{U}{U}", true);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {2}{U}{U}{U}");
        checkPermanentTapped("paid", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", true, 2 + 8);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, hurkyl, 1); // end the turn exiles it from the stack
    }

    @Test
    public void test_Flashback_CardPrepareCost_Cast() {
        // As an additional cost to cast Skeletal Scrying, exile X cards from your graveyard.
        // You draw X cards and you lose X life.
        addCard(Zone.GRAVEYARD, playerA, "Skeletal Scrying"); // {X}{B}
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2);
        //
        // When Snapcaster Mage enters, target instant or sorcery card in your graveyard gains flashback until end of turn.
        addCard(Zone.HAND, playerA, snapcaster); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, snapcaster);
        addTarget(playerA, "Skeletal Scrying");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerA, "X=2");
        setChoice(playerA, "Grizzly Bears", 2); // exile cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Skeletal Scrying", 1);
        assertExileCount(playerA, "Grizzly Bears", 2);
        assertLife(playerA, 20 - 2);
    }
}
