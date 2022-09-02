
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.a.AuratouchedMage Auratouched Mage}
 * Creature — Human Wizard 3/3
 * {5}{W}
 *
 * When Auratouched Mage enters the battlefield, search your library for an Aura card that could enchant it.
 * If Auratouched Mage is still on the battlefield, put that Aura card onto the battlefield attached to it.
 * Otherwise, reveal the Aura card and put it into your hand.
 * Then shuffle.
 *
 * @author jeffwadsworth
 */
public class AuratouchedMageTest extends CardTestPlayerBase {

    /**
     * If Auramage's type is changed in the time between it entering the battlefield and its ETB ability resolving, you
     * must be able to search for an Aura that can legally target it with whatever types it has at the ability resolves.
     *
     * Relevant Ruling:
     *      Any Aura card you find must be able to enchant Auratouched Mage as it currently exists,
     *      or as it most recently existed on the battlefield if it’s no longer on the battlefield.
     *      If an effect has made the Mage an artifact, for example, you could search for an Aura with “enchant artifact.”
     *      (2005-10-01)
     */
    @Test
    public void testAuratouchedMageEffectHasMadeIntoTypeArtifact() {
        //Expected result: An effect has made Auratouched Mage into an artifact upon entering the battlefield.  An aura that only works on artifacts should work.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.HAND, playerA, "Auratouched Mage"); //5W cost
        addCard(Zone.HAND, playerA, "Argent Mutation"); //2U cost.  Target is an artifact until end of turn
        addCard(Zone.LIBRARY, playerA, "Relic Ward"); //Only enchants an artifact permanent

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);  // Wait for Auratouched Mage to ETB
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Argent Mutation", "Auratouched Mage"); // Turn it into an artifact before its ETB ability resolves

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Auratouched Mage", 1);
        assertPermanentCount(playerA, "Relic Ward", 1);
    }

    /**
     * Auratouched Mage's ETB ability resolves with it alive, so Aura should be equipped to it.
     */
    @Test
    public void testGainsLegalAura() {
        // Expected result: Brainwash gets placed on Auratouched Mage
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Auratouched Mage");
        addCard(Zone.LIBRARY, playerA, "Brainwash");//legal aura for Auratouched Mage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Auratouched Mage", 1);
        assertPermanentCount(playerA, "Brainwash", 1);
    }

    /**
     * Auratouched Mage's ETB ability resolves, but they're dead, so the legal aura (Brainwash) gets put into Auratouched Mage's controller's hand.
     */
    @Test
    public void testAuratouchedMageNotOnBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Auratouched Mage");
        addCard(Zone.HAND, playerA, "Swords to Plowshares"); //exiles Auratouched Mage
        addCard(Zone.LIBRARY, playerA, "Brainwash"); // valid aura for Auratouched Mage
        addCard(Zone.LIBRARY, playerA, "Animate Wall"); //not a valid aura for the Auratouched Mage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);  // Wait for the Auratouched Mage to enter
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swords to Plowshares", "Auratouched Mage");  // It's ETB is still on the stack

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Auratouched Mage", 0);
        assertPermanentCount(playerA, "Brainwash", 0);
        assertHandCount(playerA, "Brainwash", 1);  // In hand since Auratouched Mage died before ability resolved
        assertLibraryCount(playerA, "Animate Wall", 1);
    }
}
