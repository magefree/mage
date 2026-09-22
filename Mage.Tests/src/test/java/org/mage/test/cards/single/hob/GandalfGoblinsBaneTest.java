package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Neutrino
 */
public class GandalfGoblinsBaneTest extends CardTestPlayerBase {

    private static final String GANDALF = "Gandalf, Goblins' Bane";
    private static final String FLAMESHAPE = "Flameshape";
    private static final String WIZARD = "Goblin Electromancer";

    @Test
    public void testFlameshapeWizardRequirement() {
        // Mana
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // First Wizard is already on the battlefield.
        // Second Wizard is in hand so we can regain the condition later.
        addCard(Zone.BATTLEFIELD, playerA, WIZARD);
        addCard(Zone.HAND, playerA, WIZARD);

        // Removal for our own Wizard
        addCard(Zone.HAND, playerA, "Murder");

        // Gandalf in hand so we can cast Flameshape
        addCard(Zone.HAND, playerA, GANDALF);

        // Make the two Flameshape cards deterministic
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Shock");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");

        // Cast Flameshape
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLAMESHAPE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Both cards were exiled
        checkExileCount(
                "after Flameshape",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                1
        );
        checkExileCount(
                "after Flameshape",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Lightning Bolt",
                1
        );

        // Wizard present: first exiled card can be played
        checkPlayableAbility(
                "Wizard present",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Cast Shock",
                true
        );

        // Actually cast the first exiled card
        castSpell(
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                playerB
        );
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Shock should have left exile and gone to the graveyard
        checkExileCount(
                "Shock played",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                0
        );
        checkGraveyardCount(
                "Shock resolved",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                1
        );

        // Destroy the Wizard
        castSpell(
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Murder",
                WIZARD
        );
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Make sure the Wizard is actually gone
        checkPermanentCount(
                "Wizard destroyed",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                WIZARD,
                0
        );

        // No Wizard: second exiled card must not be playable
        checkPlayableAbility(
                "Wizard absent",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Cast Lightning Bolt",
                false
        );

        // Cast another Wizard
        castSpell(
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                WIZARD
        );
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPermanentCount(
                "Wizard regained",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                WIZARD,
                1
        );

        // Wizard present again: second exiled card is playable again
        checkPlayableAbility(
                "Wizard present again",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Cast Lightning Bolt",
                true
        );

        // Actually cast the second exiled card
        castSpell(
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Lightning Bolt",
                playerB
        );
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkExileCount(
                "Lightning Bolt played",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Lightning Bolt",
                0
        );
        checkGraveyardCount(
                "Lightning Bolt resolved",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Lightning Bolt",
                1
        );

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testFlameshapeCardLeavesAndReturnsToExile() {
        // Mana
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // Flameshape requires us to control a Wizard
        addCard(Zone.BATTLEFIELD, playerA, WIZARD);

        // Gandalf in hand so we can cast Flameshape
        addCard(Zone.HAND, playerA, GANDALF);

        // Shred Memory will exile Shock from the graveyard after Shock
        // has first left Flameshape's exile zone.
        addCard(Zone.HAND, playerA, "Shred Memory");

        // Make the two Flameshape cards deterministic
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Shock");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");

        // Cast Flameshape
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLAMESHAPE);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkExileCount(
                "after Flameshape",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                1
        );
        checkExileCount(
                "after Flameshape",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Lightning Bolt",
                1
        );

        // Shock is one of the cards exiled by Flameshape,
        // so it can currently be cast.
        checkPlayableAbility(
                "original Shock is playable",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Cast Shock",
                true
        );

        // Cast Shock from Flameshape's exile.
        castSpell(
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                playerB
        );
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Shock has now left exile and is in the graveyard.
        checkExileCount(
                "Shock left exile",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                0
        );
        checkGraveyardCount(
                "Shock in graveyard",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                1
        );

        // Exile that same Shock again.
        // This is a new object and must no longer have Flameshape's
        // permission to be played.
        castSpell(
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shred Memory",
                "Shock"
        );
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkGraveyardCount(
                "Shock removed from graveyard",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                0
        );
        checkExileCount(
                "Shock returned to exile",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Shock",
                1
        );

        // We still control a Wizard, but this is no longer the same
        // exiled object that Flameshape originally gave permission to play.
        checkPlayableAbility(
                "re-exiled Shock is not playable",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Cast Shock",
                false
        );

        // The other card that never left Flameshape's exile
        // should still be playable.
        checkPlayableAbility(
                "original Lightning Bolt is still playable",
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "Cast Lightning Bolt",
                true
        );

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }
}