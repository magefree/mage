package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author muz
 */
public class BeamMeUpTest extends CardTestPlayerBase {

    // Open Communications
    // Draw a card.
    // Beam me up {2}{U}
    private static final String spell = "Open Communications";
    private static final String beamMeUp = "Beam me up {2}{U}";
    private static final String creature = "Grizzly Bears";

    @Before
    public void enableStrictMode() {
        setStrictChooseMode(true);
    }

    @Test
    public void testNormalCastDoesNotRequireReturningACreatureOrExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, spell);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spell);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, spell, 1);
        assertExileCount(playerA, spell, 0);
        assertTappedCount("Island", true, 1);
    }

    @Test
    public void testGraveyardCastReturnsWithoutTargetingAndPaysItsOwnCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Wall of Denial
        // Shroud (This creature can't be the target of spells or abilities.)
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Denial"); // Shroud does not prevent paying the return cost.
        // Catalyst Stone
        // Flashback costs you pay cost up to {2} less.
        addCard(Zone.BATTLEFIELD, playerA, "Catalyst Stone"); // Beam me up is not flashback.
        addCard(Zone.GRAVEYARD, playerA, spell);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, beamMeUp);
        setChoice(playerA, "Wall of Denial");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Wall of Denial", 0);
        assertHandCount(playerA, "Wall of Denial", 1);
        assertHandCount(playerA, 2);
        assertGraveyardCount(playerA, spell, 0);
        assertExileCount(playerA, spell, 1);
        assertTappedCount("Island", true, 3);
    }

    @Test
    public void testRequiresACreatureBeforeCasting() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Darksteel Relic - Artifact
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Relic");
        addCard(Zone.BATTLEFIELD, playerB, creature);
        addCard(Zone.GRAVEYARD, playerA, spell);
        // Memnite - {0}, Artifact Creature
        addCard(Zone.HAND, playerA, "Memnite");

        checkPlayableAbility("no controlled creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, beamMeUp, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("controlled creature available", 1, PhaseStep.PRECOMBAT_MAIN, playerA, beamMeUp, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, spell, 1);
        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerB, creature, 1);
    }

    @Test
    public void testOnlyFromGraveyardAtSorcerySpeed() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.BATTLEFIELD, playerA, creature, 2);
        addCard(Zone.HAND, playerA, spell);

        checkPlayableAbility("not available from hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, beamMeUp, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spell);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("available from graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, beamMeUp, true);
        checkPlayableAbility("not available during combat", 1, PhaseStep.BEGIN_COMBAT, playerA, beamMeUp, false);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, beamMeUp);
        setChoice(playerA, creature);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkPlayableAbility("not available from exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, beamMeUp, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, creature, 1);
        assertHandCount(playerA, creature, 1);
        assertHandCount(playerA, 3);
        assertExileCount(playerA, spell, 1);
        assertTappedCount("Island", true, 4);
    }

    @Test
    public void testExiledWhenCountered() {
        checkCounteredSpell("Counterspell");
    }

    @Test
    public void testExiledInsteadOfReturningToHand() {
        // Remand
        // Counter target spell. If that spell is countered this way, put it into its owner's hand
        // instead of into that player's graveyard.
        checkCounteredSpell("Remand");
    }

    private void checkCounteredSpell(String counterspell) {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, creature);
        addCard(Zone.GRAVEYARD, playerA, spell);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, counterspell);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, beamMeUp);
        setChoice(playerA, creature);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, counterspell, spell);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, creature, 0);
        assertHandCount(playerA, creature, 1);
        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, spell, 0);
        assertExileCount(playerA, spell, 1);
        assertGraveyardCount(playerB, counterspell, 1);
    }

    @Test
    public void testReturnsToOwnerRatherThanController() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        // Act of Treason
        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        addCard(Zone.HAND, playerA, "Act of Treason");
        addCard(Zone.GRAVEYARD, playerA, spell);
        addCard(Zone.BATTLEFIELD, playerB, creature);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", creature);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, beamMeUp);
        setChoice(playerA, creature);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, creature, 0);
        assertHandCount(playerB, creature, 1);
        assertPermanentCount(playerA, creature, 0);
        assertPermanentCount(playerB, creature, 0);
        assertExileCount(playerA, spell, 1);
    }
}
