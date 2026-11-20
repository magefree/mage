package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.187a
 * Mayhem is a static ability that functions while the card with mayhem is in a player’s graveyard.
 * <p>
 * 702.187b
 * “Mayhem [cost]” means “As long as you discarded this card this turn, you may cast it from your graveyard by paying
 * [cost] rather than paying its mana cost.” Casting a spell using its mayhem ability follows the rules for paying
 * alternative costs in rules 601.2b and 601.2f–h.
 *
 * @author DarioBarrabinoDev
 */
public class NormanOsbornTest extends CardTestPlayerBase {

    // https://github.com/magefree/mage/issues/16660
    @Test
    public void test_DiscardWithGreenGoblinOnBattlefield() {
        // Green Goblin, back face of Norman Osborn
        // Flying, menace
        // Spells you cast from your graveyard cost {2} less to cast.
        // Goblin Formula -- Each nonland card in your graveyard has mayhem. The mayhem cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Green Goblin"); // {1}{U}{B}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        //
        // Discard a card: Putrid Imp gains flying until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Putrid Imp");
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // bear 2/2, {1}{G}, mayhem for {G} after the {2} reduction

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Green Goblin");

        // turn 3: no nonland card in the graveyard yet, so nothing has mayhem when the bear is discarded
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, "Grizzly Bears"); // card to discard
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears with Mayhem");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Green Goblin", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_DiscardThenCastGreenGoblin() {
        // Green Goblin, back face of Norman Osborn
        // Flying, menace
        // Spells you cast from your graveyard cost {2} less to cast.
        // Goblin Formula -- Each nonland card in your graveyard has mayhem. The mayhem cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Green Goblin"); // {1}{U}{B}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        // Discard a card: Putrid Imp gains flying until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Putrid Imp");
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // bear 2/2, {1}{G}, mayhem for {G} after the {2} reduction

        // the bear is discarded before Green Goblin is on the battlefield
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, "Grizzly Bears"); // card to discard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Green Goblin");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears with Mayhem");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Green Goblin", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }
}
