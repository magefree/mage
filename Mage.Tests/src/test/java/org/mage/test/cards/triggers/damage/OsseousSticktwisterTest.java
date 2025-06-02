package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author earchip94
 */
public class OsseousSticktwisterTest extends CardTestPlayerBase {
    /**
     * Osseous Sticktwister   {1}{B}
     * Creature 2/2
     * Delirium - At the beginning of your end step, if there are four or more card types among cards in your graveyard,
     * each opponent may sacrifice a nonland permanent or discard a card. Then Osseous Sticktwister deals damage
     * equal to its power to each opponent who didn't sacrifice a permanent or discard a card this way.
     */
    @Test
    public void testDelirium() {
        addCard(Zone.BATTLEFIELD, playerA, "Osseous Sticktwister", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Priest of Titania", 1);
        addCard(Zone.HAND, playerB, "Llanowar Elves", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UNTAP);
        execute();

        // Nothing should happen since delirium is not active.
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerB, 1);
        assertHandCount(playerB, 1);
        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 0);
    }

    @Test
    public void testDiscard() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Osseous Sticktwister", 1);
        addCard(Zone.HAND, playerB, "Priest of Titania", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves", 1);

        // Setup Delirium:
        addCard(Zone.GRAVEYARD, playerA, "Sheoldred", 1); // Creature
        addCard(Zone.GRAVEYARD, playerA, "Dark Ritual", 1); // Instant
        addCard(Zone.GRAVEYARD, playerA, "Damn", 1); // Sorcery
        addCard(Zone.GRAVEYARD, playerA, "Phyrexian Arena", 1); // Enchantment

        // End Step Choice.
        setChoice(playerB, true);  // Pay the non-damage cost.
        setChoice(playerB, false); // Discard a card.
        setChoice(playerB, "Priest of Titania"); // Discard selection

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        // Delirium active, playerB discarded a card
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerB, 1);
        assertHandCount(playerB, 0);
        assertGraveyardCount(playerA, 4);
        assertGraveyardCount(playerB, 1);
    }

    @Test
    public void testSacrifice() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Osseous Sticktwister", 1);
        addCard(Zone.HAND, playerB, "Priest of Titania", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves", 1);

        // Setup Delirium:
        addCard(Zone.GRAVEYARD, playerA, "Sheoldred", 1); // Creature
        addCard(Zone.GRAVEYARD, playerA, "Dark Ritual", 1); // Instant
        addCard(Zone.GRAVEYARD, playerA, "Damn", 1); // Sorcery
        addCard(Zone.GRAVEYARD, playerA, "Phyrexian Arena", 1); // Enchantment

        // End Step Choice.
        setChoice(playerB, true);  // Pay the non-damage cost.
        setChoice(playerB, true); // Sacrifice a card.
        setChoice(playerB, "Llanowar Elves"); // Sacrifice selection

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        // Delirium active, playerB sacrificed a creature
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerB, 0);
        assertHandCount(playerB, 1);
        assertGraveyardCount(playerA, 4);
        assertGraveyardCount(playerB, 1);
    }

    @Test
    public void testDamage() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Osseous Sticktwister", 1);
        addCard(Zone.HAND, playerB, "Priest Of Titania", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves", 1);

        // Setup Delirium:
        addCard(Zone.GRAVEYARD, playerA, "Sheoldred", 1); // Creature
        addCard(Zone.GRAVEYARD, playerA, "Dark Ritual", 1); // Instant
        addCard(Zone.GRAVEYARD, playerA, "Damn", 1); // Sorcery
        addCard(Zone.GRAVEYARD, playerA, "Phyrexian Arena", 1); // Enchantment

        // End Step Choice.
        setChoice(playerB, false);  // Pay the non-damage cost.

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        // Delirium active, playerB took damage equal to Sticktwister's power,
        // playerA gained life equal to damage dealt.
        assertLife(playerA, 22);
        assertLife(playerB, 18);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerB, 1);
        assertHandCount(playerB, 1);
        assertGraveyardCount(playerA, 4);
        assertGraveyardCount(playerB, 0);
    }

    @Test
    public void testModifiedDamage() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Osseous Sticktwister", 1);
        addCard(Zone.HAND, playerA, "Unholy Strength", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Priest Of Titania", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves", 1);

        // Setup Delirium:
        addCard(Zone.GRAVEYARD, playerA, "Sheoldred", 1); // Creature
        addCard(Zone.GRAVEYARD, playerA, "Dark Ritual", 1); // Instant
        addCard(Zone.GRAVEYARD, playerA, "Damn", 1); // Sorcery
        addCard(Zone.GRAVEYARD, playerA, "Phyrexian Arena", 1); // Enchantment

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unholy Strength", "Osseous Sticktwister");

        // End Step Choice.
        setChoice(playerB, false);  // Pay the non-damage cost.

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        // Delirium active, playerB took damage equal to Sticktwister's power,
        // playerA gained life equal to damage dealt.
        assertLife(playerA, 24);
        assertLife(playerB, 16);
        assertPermanentCount(playerA, 3);
        assertPermanentCount(playerB, 1);
        assertHandCount(playerB, 1);
        assertGraveyardCount(playerA, 4);
        assertGraveyardCount(playerB, 0);
    }
}
